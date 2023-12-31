package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.model.PublicSearchParameters;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.event.dto.*;
import ru.practicum.explorewithme.event.model.AdminSearchParameters;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventFullDto> findEventsForAdmin(AdminSearchParameters parameters);

    EventFullDto findEventPublic(long eventId, HttpServletRequest request);

    List<EventShortDto> findEvents(PublicSearchParameters parameters, HttpServletRequest request);

    List<EventShortDto> findEvents(long userId, int from, int size);

    List<EventShortDto> findEventsByLocation(LocationDto location);

    EventFullDto createEvent(long userId, NewEventDto event);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest request);

    List<ParticipationRequestDto> getParticipationRequest(long userId, long eventId);

    EventRequestStatusUpdateResult changeRequestsStatus(long userId, long eventId, EventRequestStatusUpdateRequest request);

    EventFullDto editEvent(long eventId, UpdateEventAdminRequest request);
}
