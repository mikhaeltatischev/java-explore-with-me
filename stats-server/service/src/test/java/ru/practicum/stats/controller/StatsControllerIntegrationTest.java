package ru.practicum.stats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EndpointHitDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(properties = "db.name=test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(value = {"/schema.sql", "/set-up-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/set-up-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StatsControllerIntegrationTest {

    private static final String URL_HIT = "/hit";
    private static final String URL_STATS = "/stats";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private EndpointHitDto hitDto;

    @BeforeEach
    public void setUp() {
        hitDto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp("2022-09-06 11:00:23")
                .build();
    }

    @Test
    @SneakyThrows
    public void addEndpointHitWhenMethodInvokedReturnResponse() {
        mvc.perform(post(URL_HIT)
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Информация сохранена")));
    }

    @Test
    @SneakyThrows
    public void addEndpointHitWhenAppIsBlankReturnBadRequest() {
        hitDto.setApp("");

        mvc.perform(post(URL_HIT)
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void addEndpointHitWhenUriIsBlankReturnBadRequest() {
        hitDto.setUri("");

        mvc.perform(post(URL_HIT)
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void addEndpointHitWhenIpIsBlankReturnBadRequest() {
        hitDto.setIp("");

        mvc.perform(post(URL_HIT)
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void addEndpointHitWhenTimestampIsBlankReturnBadRequest() {
        hitDto.setTimestamp("");

        mvc.perform(post(URL_HIT)
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void getStatsWhenFoundThreeHitsReturnResponseWithStatsOfTwoUris() {
        mvc.perform(get(URL_STATS)
                        .queryParam("start", "2000-07-06 11:00:23")
                        .queryParam("end", "2100-10-06 11:00:23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].hits", is(2)))
                .andExpect(jsonPath("$[1].hits", is(1)));
    }

    @Test
    @SneakyThrows
    public void getStatsWhenHitsNotFoundReturnEmptyStats() {
        mvc.perform(get(URL_STATS)
                        .queryParam("start", "2100-07-06 11:00:23")
                        .queryParam("end", "2200-10-06 11:00:23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @SneakyThrows
    public void getStatsWhenUrisIsEventsReturnOneStatistic() {
        mvc.perform(get(URL_STATS)
                        .queryParam("start", "2000-07-06 11:00:23")
                        .queryParam("end", "2100-10-06 11:00:23")
                        .queryParam("uris", "/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].hits", is(2)));
    }

    @Test
    @SneakyThrows
    public void getStatsWhenUniqueIsTrueReturnOneStatistic() {
        mvc.perform(get(URL_STATS)
                        .queryParam("start", "2000-07-06 11:00:23")
                        .queryParam("end", "2100-10-06 11:00:23")
                        .queryParam("unique", "true")
                        .queryParam("uris", "/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].hits", is(1)));
    }
}