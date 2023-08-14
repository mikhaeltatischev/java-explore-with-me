package ru.practicum.explorewithme.event.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.location.dto.LocationDto;

@Getter
public class UpdateEvent {

    @Length(min = 20, max = 2000)
    protected String annotation;
    protected Long category;
    @Length(min = 20, max = 7000)
    protected String description;
    protected String eventDate;
    protected LocationDto location;
    protected Boolean paid;
    protected Integer participantLimit;
    protected Boolean requestModeration;
    protected String stateAction;
    @Length(min = 3, max = 120)
    protected String title;
}