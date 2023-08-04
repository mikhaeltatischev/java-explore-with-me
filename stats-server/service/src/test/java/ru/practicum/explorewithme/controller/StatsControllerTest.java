package ru.practicum.explorewithme.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.HttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsControllerTest {

    @Mock
    private StatsService service;

    @InjectMocks
    private StatsController controller;

    private EndpointHitDto hit;
    private HttpResponse response;
    private List<ViewStatsDto> stats;
    private StatsRequest request;

    @BeforeEach
    public void setUp() {
        hit = EndpointHitDto.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp("2022-09-06 11:00:23")
                .build();

        request = StatsRequest.builder()
                .start("2022-07-06 11:00:23")
                .end("2022-10-06 11:00:23")
                .uris(new String[]{"/events"})
                .unique(false)
                .build();

        stats = List.of(new ViewStatsDto("ewm-main-service", "/events/1"));

        response = new HttpResponse("Информация сохранена");
    }

    @Test
    public void addEndPointHitWhenMethodInvokedReturnResponse() {
        when(service.addEndpointHit(hit)).thenReturn(response);

        HttpResponse receivedResponse = controller.addEndpointHit(hit);

        assertEquals("Информация сохранена", receivedResponse.getDescription());
    }

    @Test
    public void getStatsWhenMethodInvokedReturnStats() {
        response = new HttpResponse(stats);

        when(service.getStats(any())).thenReturn(response);

        HttpResponse receivedResponse = controller.getStats(request.getStart(), request.getEnd(), request.getUris(), request.getUnique());

        assertEquals(stats, receivedResponse.getViewStatsDto());
    }
}