package ru.practicum.stats.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.HitHttpResponse;
import ru.practicum.stats.model.StatsRequest;
import ru.practicum.stats.storage.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.practicum.stats.model.DtoMapper.toDto;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mock
    private StatsRepository repository;

    @InjectMocks
    private StatsService service = new StatsServiceImpl();

    private EndpointHit hit;
    private StatsRequest request;
    private List<ViewStatsDto> stats;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private List<EndpointHit> hits;

    @BeforeEach
    public void setUp() {
        hit = EndpointHit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp(LocalDateTime.parse("2022-09-06 11:00:23", FORMATTER))
                .build();

        request = StatsRequest.builder()
                .start("2022-07-06 11:00:23")
                .end("2022-10-06 11:00:23")
                .uris(List.of("/events"))
                .unique(false)
                .build();

        start = LocalDateTime.parse(request.getStart(), FORMATTER);
        end = LocalDateTime.parse(request.getEnd(), FORMATTER);
        stats = List.of(new ViewStatsDto("ewm-main-service", "/events/1"));
        uris = request.getUris();
        hits = List.of(hit);
    }

    @Test
    public void addEndpointHitWhenMethodInvokedReturnResponse() {
        when(repository.save(hit)).thenReturn(hit);

        HitHttpResponse response = service.addEndpointHit(toDto(hit));

        assertEquals("Информация сохранена", response.getDescription());
    }

    @Test
    public void getStatsWhenExistsThreeHitsUniqueIsFalseReturnHits() {
        EndpointHit secondHit = EndpointHit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp(LocalDateTime.parse("2022-09-06 10:00:23", FORMATTER))
                .build();

        EndpointHit thirdHit = EndpointHit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/feeds/1")
                .timestamp(LocalDateTime.parse("2022-09-06 10:00:23", FORMATTER))
                .build();

        List<EndpointHit> threeHits = new ArrayList<>(hits);
        threeHits.add(secondHit);
        threeHits.add(thirdHit);

        stats.get(0).addHit(); // add one hit to first statistic
        List<ViewStatsDto> listOfThreeHits = new ArrayList<>(stats);
        listOfThreeHits.add(new ViewStatsDto("ewm-main-service", "/feeds/1"));

        when(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris)).thenReturn(threeHits);

        List<ViewStatsDto> receivedResponse = service.getStats(request);

        assertEquals(listOfThreeHits, receivedResponse);
    }

    @Test
    public void getStatsWhenUrisIsNullReturnHits() {
        request.setUris(null);

        when(repository.findAllByTimestampIsAfterAndTimestampIsBefore(start, end)).thenReturn(hits);

        List<ViewStatsDto> receivedResponse = service.getStats(request);

        assertEquals(stats, receivedResponse);
    }

    @Test
    public void getStatsWhenUniqueIsFalseReturnHits() {
        request.setUnique(false);

        when(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris)).thenReturn(hits);

        List<ViewStatsDto> receivedResponse = service.getStats(request);

        assertEquals(stats, receivedResponse);
    }
}