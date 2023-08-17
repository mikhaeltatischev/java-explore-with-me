package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.stats.model.HitHttpResponse;
import ru.practicum.stats.model.StatsRequest;
import ru.practicum.stats.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitHttpResponse addEndpointHit(@Valid @RequestBody EndpointHitDto hit) {
        return service.addEndpointHit(hit);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> getStats(@RequestParam(name = "start") String start,
                                       @RequestParam(name = "end") String end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(new StatsRequest(start, end, uris, unique));
    }

    @GetMapping("/unique")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUnique(@RequestParam(name = "uri") String uri,
                               @RequestParam(name = "ip") String ip) {
        return service.checkUnique(uri, ip);
    }
}