package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.UpdateEventAdminRequest;
import ru.practicum.explorewithme.event.service.EventServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class EventControllerTest {

    @Mock
    private EventServiceImpl eventService;

    @InjectMocks
    private EventController controller;

    private MockHttpServletRequest mockHttpServletRequest;

    private UpdateEventAdminRequest eventUpdateRequest;
    private EventFullDto eventFullDto;
    private EventShortDto eventShortDto;
    private long eventId;
    private long userId;
    private List<EventFullDto> events;
    private List<EventShortDto> shortEvents;

    @BeforeEach
    public void setUp() {
        eventId = 1L;
        userId = 1L;

        eventUpdateRequest = new UpdateEventAdminRequest();

        eventFullDto = EventFullDto.builder()
                .id(eventId)
                .build();

        eventShortDto = EventShortDto.builder()
                .id(eventId)
                .build();

        shortEvents = List.of(eventShortDto);
        events = List.of(eventFullDto);
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    public void findEventsForAdminWhenMethodInvokeReturnEvents() {
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
    public void findEventsWhenMethodInvokeReturnEvents() {
        when(eventService.findEvents(any(), any())).thenReturn(shortEvents);

        assertEquals(shortEvents, controller.findEvents("text", List.of(1L), false, "2023-08-14 19:13:51",
                "2024-08-14 19:13:51", false, "EVENT_DATE", 0, 10, mockHttpServletRequest));
    }

    @Test
    public void findEventWhenMethodInvokeReturnEvent() {
        when(eventService.findEventPublic(eventId, mockHttpServletRequest)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.findEvent(eventId, mockHttpServletRequest));
    }
}