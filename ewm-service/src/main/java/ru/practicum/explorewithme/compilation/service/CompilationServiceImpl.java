package ru.practicum.explorewithme.compilation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.compilation.exception.CompilationNotFoundException;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.practicum.explorewithme.compilation.dto.CompilationMapper.toCompilation;
import static ru.practicum.explorewithme.compilation.dto.CompilationMapper.toDto;

@Slf4j
@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final EventRepository eventRepository;
    private final CompilationRepository repository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        Set<Event> events = findEvents(newCompilation.getEvents());
        Compilation savedCompilation = repository.save(toCompilation(newCompilation, events));

        log.info("Compilation: " + savedCompilation + " saved");

        return toDto(savedCompilation);
    }

    @Override
    public CompilationDto deleteCompilation(long compId) {
        Compilation compilation = findComp(compId);
        repository.delete(compilation);

        log.info("Compilation with id: " + compId + " deleted");

        return toDto(compilation);
    }

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest request) {
        Compilation compilation = findComp(compId);
        updateFields(compilation, request);
        Compilation updatedCompilation = repository.save(compilation);

        log.info("Compilation: " + updatedCompilation + " updated");

        return toDto(updatedCompilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> compilations;

        if (pinned != null) {
            compilations = repository.getCompilationByPinned(pinned, pageRequest);
        } else {
            compilations = repository.getCompilationBy(pageRequest);
        }


        log.info("Found compilations: " + compilations);

        return toDto(compilations);
    }

    @Override
    public CompilationDto findCompilation(long compId) {
        Compilation compilation = findComp(compId);

        log.info("Found compilation: " + compilation);

        return toDto(compilation);
    }

    private Compilation findComp(long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
    }

    private Set<Event> findEvents(List<Long> events) {
        Set<Event> foundEvents;
        if (events != null) {
            foundEvents = new HashSet<>(eventRepository.findAllById(events));
        } else {
            foundEvents = null;
        }
        return foundEvents;
    }

    private void updateFields(Compilation compilation, UpdateCompilationRequest request) {
        if (request.getEvents() != null) {
            compilation.setEvents(findEvents(request.getEvents()));
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }
    }
}