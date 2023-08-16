package ru.practicum.stats.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.stats.exception.FieldIsNotValidException;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.HitHttpResponse;
import ru.practicum.stats.model.StatsRequest;
import ru.practicum.stats.storage.StatsRepository;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.stats.model.DtoMapper.toDto;
import static ru.practicum.stats.model.DtoMapper.toHit;

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
    public HitHttpResponse addEndpointHit(EndpointHitDto hit) {
        EndpointHit endpointHit = repository.save(toHit(hit));

        log.info("Hit: " + endpointHit + " saved");

        return new HitHttpResponse(MESSAGE);
    }

    @Override
    public List<ViewStatsDto> getStats(StatsRequest request) {
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

        return stats;
    }

    @Override
    public Boolean checkUnique(String uri, String ip) {
        List<EndpointHit> hits = repository.findAllByIpAndUri(ip, uri);

        if (hits.isEmpty()) {
            return true;
        }

        return false;
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
        decodeRequest(request);
        LocalDateTime start = LocalDateTime.parse(request.getStart(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(request.getEnd(), FORMATTER);
        checkDate(start, end);
        List<EndpointHit> hits = new ArrayList<>();

        if (request.getUris() != null && request.getUnique()) {
            hits.addAll(repository.findAllUniqueAndByUri(start, end, request.getUris()));
        } else if (request.getUris() != null) {
            hits.addAll(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, request.getUris()));
        } else if (request.getUnique()) {
            hits.addAll(repository.findAllUnique(start, end));
        } else {
            hits.addAll(repository.findAllByTimestampIsAfterAndTimestampIsBefore(start, end));
        }
        return hits;
    }

    private void decodeRequest(StatsRequest request) {
        request.setStart(URLDecoder.decode(request.getStart()));
        request.setEnd(URLDecoder.decode(request.getEnd()));
    }

    private void checkDate(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new FieldIsNotValidException("Start, End");
        }
    }
}