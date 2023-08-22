package ru.practicum.explorewithme.location.dto;

import ru.practicum.explorewithme.location.model.Location;

import java.util.List;
import java.util.stream.Collectors;

public class LocationMapper {

    public static LocationDto toDto(Location location) {
        String name = location.getName();
        String address = location.getAddress();
        String type = location.getType();
        double radius;

        if (location.getRadius() != null) {
            radius = location.getRadius();
        } else {
            radius = 0.0;
        }

        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .radius(radius)
                .name(name)
                .address(address)
                .type(type)
                .build();
    }

    public static Location toLocation(LocationDto location) {
        String name = location.getName();
        String address = location.getAddress();
        String type = location.getType();

        return Location.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .radius(location.getRadius())
                .name(name)
                .address(address)
                .type(type)
                .build();
    }

    public static List<LocationDto> toDto(List<Location> locations) {
        return locations.stream()
                .map(LocationMapper::toDto)
                .collect(Collectors.toList());
    }
}