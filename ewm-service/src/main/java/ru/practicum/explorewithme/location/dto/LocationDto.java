package ru.practicum.explorewithme.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public final class LocationDto {

    private final float lat;
    private final float lon;
}