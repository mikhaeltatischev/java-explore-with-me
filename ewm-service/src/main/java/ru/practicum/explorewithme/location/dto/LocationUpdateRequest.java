package ru.practicum.explorewithme.location.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class LocationUpdateRequest {

    private Double radius;
    @NotBlank
    private String name;
}