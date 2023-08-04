package ru.practicum.explorewithme.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.HttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;
import ru.practicum.explorewithme.storage.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.model.DtoMapper.toDto;

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
    private Collection<String> uris;
    private List<EndpointHit> hits;
    private HttpResponse response;

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
                .uris(new String[]{"/events"})
                .unique(false)
                .build();

        start = LocalDateTime.parse(request.getStart(), FORMATTER);
        end = LocalDateTime.parse(request.getEnd(), FORMATTER);
        stats = List.of(new ViewStatsDto("ewm-main-service", "/events/1"));
        uris = Arrays.asList(request.getUris());
        hits = List.of(hit);
        response = new HttpResponse(stats);
    }

    @Test
    public void addEndpointHitWhenMethodInvokedReturnResponse() {
        when(repository.save(hit)).thenReturn(hit);

        HttpResponse response = service.addEndpointHit(toDto(hit));

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
        List<ViewStatsDto> statsOfTwoHits = new ArrayList<>(stats);
        statsOfTwoHits.add(new ViewStatsDto("ewm-main-service", "/feeds/1"));

        response = new HttpResponse(statsOfTwoHits);

        when(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris)).thenReturn(threeHits);

        HttpResponse receivedResponse = service.getStats(request);

        assertEquals(response.getViewStatsDto(), receivedResponse.getViewStatsDto());
    }

    @Test
    public void getStatsWhenUrisIsNullReturnHits() {
        request.setUris(null);

        when(repository.findAllByTimestampIsAfterAndTimestampIsBefore(start, end)).thenReturn(hits);

        HttpResponse receivedResponse = service.getStats(request);

        assertEquals(response.getViewStatsDto(), receivedResponse.getViewStatsDto());
    }

    @Test
    public void getStatsWhenUniqueIsTrueReturnHits() {
        request.setUnique(true);

        when(repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris)).thenReturn(hits);

        HttpResponse receivedResponse = service.getStats(request);

        assertEquals(response.getViewStatsDto(), receivedResponse.getViewStatsDto());
    }
}