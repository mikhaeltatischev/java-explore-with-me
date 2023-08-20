package ru.practicum.explorewithme.location.service;

import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.location.dto.LocationUpdateRequest;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);

    LocationDto updateLocation(long locationId, LocationUpdateRequest updateRequest);

    LocationDto deleteLocation(long locationId);

    LocationDto findLocationById(long locationId);

    List<LocationDto> getLocations(int from, int size);

    List<LocationDto> findLocations(String text, int from, int size);
}