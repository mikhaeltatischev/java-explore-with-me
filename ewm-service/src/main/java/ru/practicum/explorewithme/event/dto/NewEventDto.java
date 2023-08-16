package ru.practicum.explorewithme.event.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public final class NewEventDto {

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;
    @NotBlank
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    @Length(min = 3, max = 120)
    private String title;
}
