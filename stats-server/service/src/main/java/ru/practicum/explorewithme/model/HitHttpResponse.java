package ru.practicum.explorewithme.model;

import lombok.Data;

@Data
public class HitHttpResponse {

    private String description;

    public HitHttpResponse(String description) {
        this.description = description;
    }
}
