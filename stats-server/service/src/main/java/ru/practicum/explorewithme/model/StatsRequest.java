package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class StatsRequest {

    private String start;
    private String end;
    private String[] uris;
    private Boolean unique;
}
