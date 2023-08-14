package ru.practicum.explorewithme.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {

    private float lat;
    private float lon;
}