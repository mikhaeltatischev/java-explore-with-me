package ru.practicum.explorewithme.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@ToString
@AllArgsConstructor
public final class LocationDto {

    @NotNull
    private final Double lat;
    @NotNull
    private final Double lon;
    @Positive
    private final Double radius;
    private final String name;
    private final String address;
    private final String type;
}