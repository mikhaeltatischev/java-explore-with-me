package ru.practicum.explorewithme.model;

import lombok.Data;
import ru.practicum.explorewithme.dto.ViewStatsDto;

import java.util.List;

@Data
public class HttpResponse {

    private String description;
    private List<ViewStatsDto> viewStatsDto;

    public HttpResponse(String description) {
        this.description = description;
    }

    public HttpResponse(List<ViewStatsDto> viewStatsDto) {
        this.viewStatsDto = viewStatsDto;
    }
}
