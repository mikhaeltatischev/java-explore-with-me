package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.model.HttpResponse;
import ru.practicum.explorewithme.model.StatsRequest;
import ru.practicum.explorewithme.service.StatsService;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpResponse addEndpointHit(@Valid @RequestBody EndpointHitDto hit) {
        return service.addEndpointHit(hit);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public HttpResponse getStats(@RequestParam(name = "start") String start,
                                 @RequestParam(name = "end") String end,
                                 @RequestParam(required = false) String[] uris,
                                 @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(new StatsRequest(start, end, uris, unique));
    }
}