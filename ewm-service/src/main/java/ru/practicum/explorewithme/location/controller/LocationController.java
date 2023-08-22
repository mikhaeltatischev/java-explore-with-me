package ru.practicum.explorewithme.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.location.dto.LocationUpdateRequest;
import ru.practicum.explorewithme.location.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @PostMapping("/admin/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@Valid @RequestBody LocationDto locationDto) {
        return service.createLocation(locationDto);
    }

    @PatchMapping("/admin/locations/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto updateLocation(@PathVariable long locationId,
                                      @Valid @RequestBody LocationUpdateRequest updateRequest) {
        return service.updateLocation(locationId, updateRequest);
    }

    @DeleteMapping("/admin/locations/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LocationDto deleteLocation(@PathVariable long locationId) {
        return service.deleteLocation(locationId);
    }

    @GetMapping("/admin/locations/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto findLocationById(@PathVariable long locationId) {
        return service.findLocationById(locationId);
    }

    @GetMapping("/admin/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getLocations(@Min(0) @RequestParam(defaultValue = "0") int from,
                                          @Min(1) @RequestParam(defaultValue = "10") int size) {
        return service.getLocations(from, size);
    }

    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> findLocationsPublic(@RequestParam(required = false) String text,
                                                 @Min(0) @RequestParam(defaultValue = "0") int from,
                                                 @Min(1) @RequestParam(defaultValue = "10") int size) {
        return service.findLocations(text, from, size);
    }
}