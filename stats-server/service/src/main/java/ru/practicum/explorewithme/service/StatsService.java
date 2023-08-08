package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.HitHttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;

import java.util.List;

public interface StatsService {

    HitHttpResponse addEndpointHit(EndpointHitDto hit);

    List<ViewStatsDto> getStats(StatsRequest request);
}
