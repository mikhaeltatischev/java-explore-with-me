package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.service.CategoryService;
import ru.practicum.explorewithme.event.dto.*;
import ru.practicum.explorewithme.event.model.AdminSearchParameters;
import ru.practicum.explorewithme.event.model.PublicSearchParameters;
import ru.practicum.explorewithme.event.service.EventService;
import ru.practicum.explorewithme.location.dto.LocationDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final CategoryService categoryService;

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

    @GetMapping("/locations/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findEventsByLocation(@RequestBody LocationDto location) {
        return service.findEventsByLocation(location);
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

    @PostMapping("/events/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto category) {
        return categoryService.create(category);
    }

    @DeleteMapping("/events/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryDto deleteCategory(@PathVariable(name = "catId") long id) {
        return categoryService.delete(id);
    }

    @PatchMapping("/events/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable(name = "catId") long id,
                                      @Valid @RequestBody CategoryDto category) {
        return categoryService.update(id, category);
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId,
                                    @Valid @RequestBody NewEventDto event) {
        return service.createEvent(userId, event);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable long userId,
                                 @PathVariable long eventId) {
        return service.getEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest request) {
        return service.updateEvent(userId, eventId, request);
    }
}