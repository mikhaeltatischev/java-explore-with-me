package ru.practicum.explorewithme.event.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explorewithme.category.dto.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.AdminSearchParameters;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Status;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.location.model.Location;
import ru.practicum.explorewithme.location.repository.LocationRepository;
import ru.practicum.explorewithme.request.repository.ParticipationRequestRepository;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.location.dto.LocationMapper.toDto;
import static ru.practicum.explorewithme.user.dto.UserMapper.toShortDto;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mock
    private EventRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ParticipationRequestRepository requestRepository;

    @InjectMocks
    private EventServiceImpl service;

    private List<String> statuses;
    private List<Status> states;
    private AdminSearchParameters parameters;
    private PageRequest pageRequest;
    private List<EventFullDto> fullEvents;
    private List<EventShortDto> shortEvents;
    private List<Category> categories;
    private List<Event> events;
    private List<User> users;
    private EventShortDto shortEvent;
    private EventFullDto eventFullDto;
    private Category category;
    private Event event;
    private User user;
    private long eventId;
    private long userId;
    private long catId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Location location;

    @BeforeEach
    public void setUp() {
        eventId = 1L;
        userId = 1L;
        catId = 1L;

        states = List.of(Status.PUBLISHED);
        start = LocalDateTime.parse("2023-08-14T19:13:51");
        end = LocalDateTime.parse("2023-08-14T19:13:51");

        pageRequest = PageRequest.of(0, 10);

        statuses = List.of("PUBLISHED");

        category = Category.builder()
                .id(catId)
                .name("drama")
                .build();

        user = User.builder()
                .id(userId)
                .name("name")
                .email("mail@mail.ru")
                .build();

        shortEvent = EventShortDto.builder()
                .id(eventId)
                .build();

        location = Location.builder()
                .id(1L)
                .lat(2.22)
                .lon(2.22)
                .build();

        event = Event.builder()
                .id(userId)
                .createdOn(LocalDateTime.now())
                .eventDate(LocalDateTime.now().plusDays(2))
                .location(location)
                .description("description")
                .annotation("annotation")
                .confirmedRequests(0L)
                .category(category)
                .initiator(user)
                .views(0L)
                .state(Status.PUBLISHED)
                .build();

        eventFullDto = EventFullDto.builder()
                .id(userId)
                .location(toDto(location))
                .createdOn(LocalDateTime.now().format(FORMATTER))
                .eventDate(LocalDateTime.now().plusDays(2).format(FORMATTER))
                .description("description")
                .annotation("annotation")
                .confirmedRequests(0L)
                .category(CategoryMapper.toDto(category))
                .initiator(toShortDto(user))
                .views(0L)
                .state("PUBLISHED")
                .build();

        fullEvents = List.of(eventFullDto);
        shortEvents = List.of(shortEvent);
        events = List.of(event);
        users = List.of(user);
        categories = List.of(category);
    }

    @Test
    public void findEventsWhenMethodInvokeReturnEvents() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.findAllByInitiatorId(eventId, pageRequest)).thenReturn(events);

        assertEquals(shortEvents, service.findEvents(userId, 0, 10));
    }

    @Test
    public void findEventsForAdminWhenMethodInvokeReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .categories(List.of(catId))
                .rangeEnd(start)
                .rangeStart(end)
                .states(statuses)
                .users(List.of(userId))
                .build();

        when(userRepository.findAllById(List.of(userId))).thenReturn(List.of(user));
        when(categoryRepository.findAllById(List.of(catId))).thenReturn(List.of(category));
        when(repository.findAllByInitiatorInAndStateInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(users, states,
                categories, start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenCategoriesIsNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .rangeEnd(start)
                .rangeStart(end)
                .states(statuses)
                .users(List.of(userId))
                .build();

        when(userRepository.findAllById(List.of(userId))).thenReturn(List.of(user));
        when(repository.findAllByInitiatorInAndStateInAndEventDateIsAfterAndEventDateIsBefore(users, states,
                start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenStatusesIsNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .categories(List.of(catId))
                .rangeEnd(start)
                .rangeStart(end)
                .users(List.of(userId))
                .build();

        when(userRepository.findAllById(List.of(userId))).thenReturn(List.of(user));
        when(categoryRepository.findAllById(List.of(catId))).thenReturn(List.of(category));
        when(repository.findAllByInitiatorInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(users,
                categories, start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenStatusesAndCategoriesAreNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .rangeEnd(start)
                .rangeStart(end)
                .users(List.of(userId))
                .build();

        when(userRepository.findAllById(List.of(userId))).thenReturn(List.of(user));
        when(repository.findAllByInitiatorInAndEventDateIsAfterAndEventDateIsBefore(users, start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenUsersAndCategoriesAreNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .rangeEnd(start)
                .rangeStart(end)
                .categories(List.of(catId))
                .build();

        when(categoryRepository.findAllById(List.of(catId))).thenReturn(List.of(category));
        when(repository.findAllByCategoryInAndEventDateIsAfterAndEventDateIsBefore(categories,
                start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenUsersAndStatusesAreNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .rangeEnd(start)
                .rangeStart(end)
                .states(statuses)
                .build();

        when(repository.findAllByStateInAndEventDateIsAfterAndEventDateIsBefore(states,
                start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }

    @Test
    public void findEventsForAdminWhenUsersAndCategoriesAndStatusesAreNullReturnEvents() {
        parameters = AdminSearchParameters.builder()
                .pageRequest(pageRequest)
                .rangeEnd(start)
                .rangeStart(end)
                .build();

        when(repository.findAllByEventDateIsAfterAndEventDateIsBefore(start, end, pageRequest)).thenReturn(events);

        assertEquals(fullEvents, service.findEventsForAdmin(parameters));
    }
}