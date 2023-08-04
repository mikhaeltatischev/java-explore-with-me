package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.HttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;

public interface StatsService {

    HttpResponse addEndpointHit(EndpointHitDto hit);

    HttpResponse getStats(StatsRequest request);
}
