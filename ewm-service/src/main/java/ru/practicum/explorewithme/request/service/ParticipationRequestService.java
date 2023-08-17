package ru.practicum.explorewithme.request.service;

import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    List<ParticipationRequestDto> getRequests(long userId);

    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}