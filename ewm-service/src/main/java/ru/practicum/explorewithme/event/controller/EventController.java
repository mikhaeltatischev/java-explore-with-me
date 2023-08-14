package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.PublicSearchParameters;
import ru.practicum.explorewithme.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findEvents(@RequestParam(required = false, name = "text") String text,
                                          @RequestParam(required = false, name = "categories") List<Long> categories,
                                          @RequestParam(required = false, name = "paid") Boolean paid,
                                          @RequestParam(required = false, name = "rangeStart") String rangeStart,
                                          @RequestParam(required = false, name = "rangeEnd") String rangeEnd,
                                          @RequestParam(required = false, name = "onlyAvailable") boolean onlyAvailable,
                                          @RequestParam(required = false, name = "sort") String sort,
                                          @RequestParam(defaultValue = "0", name = "from") int from,
                                          @RequestParam(defaultValue = "10", name = "size") int size,
                                          HttpServletRequest request) {
        return service.findEvents(PublicSearchParameters.of(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size), request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findEvent(@PathVariable long id,
                                  HttpServletRequest request) {
        return service.findEventPublic(id, request);
    }
}