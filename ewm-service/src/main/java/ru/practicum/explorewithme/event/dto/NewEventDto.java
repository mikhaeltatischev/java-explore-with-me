package ru.practicum.explorewithme.event.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@AllArgsConstructor
public final class NewEventDto {

    @NotBlank
    @Length(min = 20, max = 2000)
    private final String annotation;
    @NotNull
    private final Long category;
    @NotBlank
    @Length(min = 20, max = 7000)
    private final String description;
    @NotBlank
    private final String eventDate;
    @NotNull
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    @NotBlank
    @Length(min = 3, max = 120)
    private final String title;
}
