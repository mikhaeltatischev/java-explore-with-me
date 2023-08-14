package ru.practicum.explorewithme.location.dto;

import ru.practicum.explorewithme.location.model.Location;

public class LocationMapper {

    public static LocationDto toDto(Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }
}