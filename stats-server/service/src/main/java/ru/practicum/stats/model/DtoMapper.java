package ru.practicum.stats.model;

import ru.practicum.explorewithme.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHit toHit(EndpointHitDto hit) {
        String app = hit.getApp();
        String uri = hit.getUri();
        String ip = hit.getIp();
        LocalDateTime timestamp = LocalDateTime.parse(hit.getTimestamp(), FORMATTER);

        return new EndpointHit(app, uri, ip, timestamp);
    }

    public static EndpointHitDto toDto(EndpointHit hit) {
        String app = hit.getApp();
        String uri = hit.getUri();
        String ip = hit.getIp();
        String timestamp = hit.getTimestamp().format(FORMATTER);

        return new EndpointHitDto(app, uri, ip, timestamp);
    }

    public static List<EndpointHitDto> toDto(Collection<EndpointHit> hits) {
        return hits.stream().map(DtoMapper::toDto).collect(Collectors.toList());
    }
}
