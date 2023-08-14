package ru.practicum.explorewithme.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UniqueResponse {
    private final Boolean isUnique;
}