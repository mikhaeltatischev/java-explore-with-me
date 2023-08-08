package ru.practicum.explorewithme.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(of = {"app", "uri"})
@NoArgsConstructor
@Data
public class ViewStatsDto implements Comparable<ViewStatsDto> {

    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @Positive
    @NotNull
    private Integer hits;

    public ViewStatsDto(String app, String uri) {
        this.app = app;
        this.uri = uri;
        this.hits = 1;
    }

    public void addHit() {
        hits++;
    }

    @Override
    public int compareTo(ViewStatsDto o) {
        return o.getHits().compareTo(this.getHits());
    }
}