package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.UpdateEventAdminRequest;
import ru.practicum.explorewithme.event.model.AdminSearchParameters;
import ru.practicum.explorewithme.event.model.PublicSearchParameters;
import ru.practicum.explorewithme.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findEvents(@RequestParam(required = false, name = "text") String text,
                                          @RequestParam(required = false, name = "categories") List<Long> categories,
                                          @RequestParam(required = false, name = "paid") Boolean paid,
                                          @RequestParam(required = false, name = "rangeStart") String rangeStart,
                                          @RequestParam(required = false, name = "rangeEnd") String rangeEnd,
                                          @RequestParam(required = false, name = "onlyAvailable") boolean onlyAvailable,
                                          @RequestParam(required = false, name = "sort") String sort,
                                          @Min(0) @RequestParam(defaultValue = "0", name = "from") int from,
                                          @Min(1) @RequestParam(defaultValue = "10", name = "size") int size,
                                          HttpServletRequest request) {
        return service.findEvents(PublicSearchParameters.of(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size), request);
    }

    @GetMapping("/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> findEventsForAdmin(@RequestParam(required = false) List<Long> users,
                                                 @RequestParam(required = false) List<String> states,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @Min(0) @RequestParam(defaultValue = "0") int from,
                                                 @Min(1) @RequestParam(defaultValue = "10") int size) {
        return service.findEventsForAdmin(AdminSearchParameters.of(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findEvent(@PathVariable long id,
                                  HttpServletRequest request) {
        return service.findEventPublic(id, request);
    }


    @PatchMapping("/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto editEvent(@PathVariable long eventId,
                                  @Valid @RequestBody UpdateEventAdminRequest request) {
        return service.editEvent(eventId, request);
    }
}