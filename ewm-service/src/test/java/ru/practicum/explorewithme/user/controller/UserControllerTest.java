package ru.practicum.explorewithme.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.event.dto.*;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.service.ParticipationRequestService;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private ParticipationRequestService requestService;

    @Mock
    private UserService userService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private UserController controller;

    private List<UserDto> users;
    private List<EventShortDto> events;
    private UserDto userDto;
    private EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest;
    private EventRequestStatusUpdateResult eventRequestStatusUpdateResult;
    private EventShortDto eventShortDto;
    private ParticipationRequestDto participationRequestDto;
    private long userId;
    private long eventId;
    private List<Long> ids;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        eventId = 1L;

        eventShortDto = EventShortDto.builder()
                .title("title")
                .build();

        userDto = UserDto.builder()
                .id(userId)
                .email("mail@mail.ru")
                .name("name name")
                .build();

        participationRequestDto = new ParticipationRequestDto();

        eventRequestStatusUpdateRequest = new EventRequestStatusUpdateRequest();

        eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();

        users = List.of(userDto);
        events = List.of(eventShortDto);
        ids = List.of(userId);
    }

    @Test
    public void getUsersWhenMethodInvokeReturnUsers() {
        when(userService.get(ids, 0, 10)).thenReturn(users);

        assertEquals(users, controller.getUsers(ids, 0, 10));
    }

    @Test
    public void createUserWhenMethodInvokeReturnUser() {
        when(userService.add(userDto)).thenReturn(userDto);

        assertEquals(userDto, controller.createUser(userDto));
    }

    @Test
    public void deleteUserWhenMethodInvokeReturnUser() {
        when(userService.delete(userId)).thenReturn(userDto);

        assertEquals(userDto, controller.deleteUser(userId));
    }

    @Test
    public void getEventsForCurrentUserWhenMethodInvokeReturnEvents() {
        when(eventService.findEvents(userId, 0, 10)).thenReturn(events);

        assertEquals(events, controller.getEventsForCurrentUser(userId, 0, 10));
    }

    @Test
    public void getParticipationRequestWhenMethodInvokeReturnParticipationRequests() {
        when(eventService.getParticipationRequest(userId, eventId)).thenReturn(List.of(participationRequestDto));

        assertEquals(List.of(participationRequestDto), controller.getParticipationRequest(userId, eventId));
    }

    @Test
    public void updateParticipationRequestWhenMethodInvokeReturnParticipationRequest() {
        when(eventService.changeRequestsStatus(userId, eventId, eventRequestStatusUpdateRequest)).thenReturn(eventRequestStatusUpdateResult);

        assertEquals(eventRequestStatusUpdateResult, controller.updateParticipationRequest(userId, eventId, eventRequestStatusUpdateRequest));
    }

    @Test
    public void getRequestsWhenMethodInvokeReturnRequests() {
        when(requestService.getRequests(userId)).thenReturn(List.of(participationRequestDto));

        assertEquals(List.of(participationRequestDto), controller.getRequests(userId));
    }

    @Test
    public void createRequestWhenMethodInvokeReturnRequest() {
        when(requestService.createRequest(userId, eventId)).thenReturn(participationRequestDto);

        assertEquals(participationRequestDto, controller.createRequest(userId, eventId));
    }

    @Test
    public void cancelRequestWhenMethodInvokeReturnRequest() {
        when(requestService.cancelRequest(userId, eventId)).thenReturn(participationRequestDto);

        assertEquals(participationRequestDto, controller.cancelRequest(userId, eventId));
    }
}