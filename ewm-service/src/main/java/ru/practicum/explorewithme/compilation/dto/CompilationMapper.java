package ru.practicum.explorewithme.compilation.dto;

import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.event.dto.EventMapper.toShortDto;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto compilation, Set<Event> events) {
        boolean pinned = compilation.isPinned();
        String title = compilation.getTitle();

        return Compilation.builder()
                .events(events)
                .pinned(pinned)
                .title(title)
                .build();
    }

    public static CompilationDto toDto(Compilation compilation) {
        Long id = compilation.getId();
        List<EventShortDto> events;
        boolean pinned = compilation.isPinned();
        String title = compilation.getTitle();

        if (compilation.getEvents() != null) {
            events = toShortDto(new ArrayList<>(compilation.getEvents()));
        } else {
            events = null;
        }

        return new CompilationDto(id, events, pinned, title);
    }

    public static List<CompilationDto> toDto(List<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());
    }
}