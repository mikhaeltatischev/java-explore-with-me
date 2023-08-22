package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.category.service.CategoryService;
import ru.practicum.explorewithme.category.service.CategoryServiceImpl;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventAdminRequest;
import ru.practicum.explorewithme.event.dto.UpdateEventUserRequest;
import ru.practicum.explorewithme.event.service.EventServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class EventControllerTest {

    @Mock
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    private EventServiceImpl eventService;

    @InjectMocks
    private EventController controller;

    private UpdateEventAdminRequest eventUpdateRequest;
    private UpdateEventUserRequest updateEventUserRequest;
    private EventFullDto eventFullDto;
    private NewEventDto newEvent;
    private long eventId;
    private long userId;
    private List<EventFullDto> events;

    @BeforeEach
    public void setUp() {
        eventId = 1L;
        userId = 1L;

        eventUpdateRequest = new UpdateEventAdminRequest();
        updateEventUserRequest = new UpdateEventUserRequest();

        eventFullDto = EventFullDto.builder()
                .id(eventId)
                .build();

        events = List.of(eventFullDto);
    }

    @Test
    public void findEventsWhenMethodInvokeReturnEvents() {
        when(eventService.findEventsForAdmin(any())).thenReturn(events);

        assertEquals(events, controller.findEventsForAdmin(List.of(userId), List.of("PUBLISHED"), List.of(1L),
                "2023-08-14 19:13:51", "2024-08-14 19:13:51", 0, 10));
    }

    @Test
    public void editEventWhenMethodInvokeReturnEvent() {
        when(eventService.editEvent(eventId, eventUpdateRequest)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.editEvent(eventId, eventUpdateRequest));
    }

    @Test
    public void createEventWhenMethodInvokeReturnEvent() {
        when(eventService.createEvent(userId, newEvent)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.createEvent(userId, newEvent));
    }

    @Test
    public void getEventWhenMethodInvokeReturnEvent() {
        when(eventService.getEvent(userId, eventId)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.getEvent(userId, eventId));
    }

    @Test
    public void updateEventWhenMethodInvokeReturnEvent() {
        when(eventService.updateEvent(userId, eventId, updateEventUserRequest)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.updateEvent(userId, eventId, updateEventUserRequest));
    }
}