package ru.practicum.explorewithme.compilation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.compilation.exception.CompilationNotFoundException;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.event.dto.EventMapper.*;

@ExtendWith(MockitoExtension.class)
public class CompilationServiceTest {

    @Mock
    private CompilationRepository compilationRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CompilationServiceImpl compilationService;

    private List<Event> events;
    private UpdateCompilationRequest request;
    private NewCompilationDto newCompilationDto;
    private CompilationDto compilationDto;
    private Event event;
    private Compilation compilation;
    private Category category;
    private User user;
    private long compId;
    private long eventId;
    private String title;

    @BeforeEach
    public void setUp() {
        title = "title";
        compId = 1L;
        eventId = 1L;

        user = User.builder()
                .id(1L)
                .email("mail@mail.ru")
                .name("name name")
                .build();

        category = Category.builder()
                .id(1L)
                .name("drama")
                .build();

        event = Event.builder()
                .id(1L)
                .createdOn(LocalDateTime.now())
                .eventDate(LocalDateTime.now().plusDays(2))
                .description("description")
                .annotation("annotation")
                .confirmedRequests(0L)
                .category(category)
                .initiator(user)
                .views(0L)
                .build();

        events = List.of(event);

        compilationDto = CompilationDto.builder()
                .id(compId)
                .title(title)
                .events(toShortDto(events))
                .build();

        newCompilationDto = NewCompilationDto.builder()
                .title(title)
                .events(List.of(eventId))
                .build();

        compilation = Compilation.builder()
                .id(compId)
                .title(title)
                .events(new HashSet<>(events))
                .build();

        request = UpdateCompilationRequest.builder()
                .events(List.of(eventId))
                .pinned(false)
                .title("title")
                .build();
    }

    @Test
    public void createCompilationWhenMethodInvokeReturnCompilation() {
        when(eventRepository.findAllById(List.of(eventId))).thenReturn(events);
        when(compilationRepository.save(any())).thenReturn(compilation);

        assertEquals(compilationDto, compilationService.createCompilation(newCompilationDto));
    }

    @Test
    public void deleteCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationRepository.findById(compId)).thenReturn(Optional.of(compilation));

        assertEquals(compilationDto, compilationService.deleteCompilation(compId));

        verify(compilationRepository).delete(compilation);
    }

    @Test
    public void updateCompilationWhenMethodInvokeReturnCompilation() {
        when(eventRepository.findAllById(List.of(eventId))).thenReturn(events);
        when(compilationRepository.findById(compId)).thenReturn(Optional.of(compilation));
        when(compilationRepository.save(any())).thenReturn(compilation);

        assertEquals(compilationDto, compilationService.updateCompilation(compId, request));
    }

    @Test
    public void getCompilationsWhenMethodInvokeReturnCompilations() {
        when(compilationRepository.getCompilationByPinned(true, PageRequest.of(0, 10))).thenReturn(List.of(compilation));

        assertEquals(List.of(compilationDto), compilationService.getCompilations(true, 0, 10));
    }

    @Test
    public void getCompilationsWhenPinnedIsNullReturnCompilations() {
        when(compilationRepository.getCompilationBy(PageRequest.of(0, 10))).thenReturn(List.of(compilation));

        assertEquals(List.of(compilationDto), compilationService.getCompilations(null, 0, 10));
    }

    @Test
    public void findCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationRepository.findById(compId)).thenReturn(Optional.of(compilation));

        assertEquals(compilationDto, compilationService.findCompilation(compId));
    }

    @Test
    public void findCompilationWhenCompilationNotFoundReturnCompilation() {
        when(compilationRepository.findById(compId)).thenThrow(CompilationNotFoundException.class);

        assertThrows(CompilationNotFoundException.class, () -> compilationService.findCompilation(compId));
    }
}