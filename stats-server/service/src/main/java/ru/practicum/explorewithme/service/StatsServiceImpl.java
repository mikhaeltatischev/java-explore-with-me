package ru.practicum.explorewithme.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.HttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;
import ru.practicum.explorewithme.storage.StatsRepository;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.model.DtoMapper.toDto;
import static ru.practicum.explorewithme.model.DtoMapper.toHit;

@Service
@NoArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String MESSAGE = "Информация сохранена";

    private StatsRepository repository;

    @Autowired
    public StatsServiceImpl(StatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public HttpResponse addEndpointHit(EndpointHitDto hit) {
        EndpointHit endpointHit = repository.save(toHit(hit));

        log.info("Hit: " + endpointHit + " saved");

        return new HttpResponse(MESSAGE);
    }

    @Override
    public HttpResponse getStats(StatsRequest request) {
        List<EndpointHit> hits = findHits(request);
        List<EndpointHitDto> hitsDto = toDto(hits);

        if (request.getUnique()) {
            Set<EndpointHitDto> uniqueHits = new HashSet<>(hitsDto);
            hitsDto = new ArrayList<>(uniqueHits);
        }

        List<ViewStatsDto> stats = createStats(hitsDto).stream()
                .sorted(ViewStatsDto::compareTo)
                .collect(Collectors.toList());
        log.info("Received statistics: " + stats);

        return new HttpResponse(stats);
    }

    private List<ViewStatsDto> createStats(List<EndpointHitDto> hits) {
        List<ViewStatsDto> stats = new ArrayList<>();

        for (EndpointHitDto hit : hits) {
            ViewStatsDto statDto = new ViewStatsDto(hit.getApp(), hit.getUri());

            if (stats.contains(statDto)) {
                stats.forEach((stat) -> {
                    if (stat.equals(statDto)) {
                        stat.addHit();
                    }
                });
            } else {
                stats.add(statDto);
            }
        }
        return stats;
    }

    private List<EndpointHit> findHits(StatsRequest request) {
        decode(request);
        LocalDateTime start = LocalDateTime.parse(request.getStart(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(request.getEnd(), FORMATTER);
        List<EndpointHit> hits = new ArrayList<>();

        if (request.getUris() == null) {
            hits.addAll(repository.findAllByTimestampIsAfterAndTimestampIsBefore(start, end));
        } else {
            Collection<String> uris = List.of(request.getUris());
            hits.addAll(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris));
        }
        return hits;
    }

    private void decode(StatsRequest request) {
        request.setStart(URLDecoder.decode(request.getStart()));
        request.setEnd(URLDecoder.decode(request.getEnd()));
    }
}