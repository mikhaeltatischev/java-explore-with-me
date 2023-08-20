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
    private Double lat;
    @NotNull
    private Double lon;
    @Positive
    private Double radius;
    private String name;
    private String address;
    private String type;
}