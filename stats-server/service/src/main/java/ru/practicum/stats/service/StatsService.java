package ru.practicum.stats.service;

import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.stats.model.HitHttpResponse;
import ru.practicum.stats.model.StatsRequest;

import java.util.List;

public interface StatsService {

    HitHttpResponse addEndpointHit(EndpointHitDto hit);

    List<ViewStatsDto> getStats(StatsRequest request);

    Boolean checkUnique(String uri, String ip);
}
