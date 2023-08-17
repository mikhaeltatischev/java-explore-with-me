package ru.practicum.stats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class StatsRequest {

    private String start;
    private String end;
    private List<String> uris;
    private Boolean unique;
}
