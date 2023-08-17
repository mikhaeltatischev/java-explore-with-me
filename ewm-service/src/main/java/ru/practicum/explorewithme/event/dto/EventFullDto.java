package ru.practicum.explorewithme.event.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.user.dto.UserShortDto;

@Getter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public final class EventFullDto {

    private final Long id;
    private final String annotation;
    private final CategoryDto category;
    private final Long confirmedRequests;
    private final String createdOn;
    private final String description;
    private final String eventDate;
    private final UserShortDto initiator;
    private final LocationDto location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final String publishedOn;
    private final Boolean requestModeration;
    private final String state;
    private final String title;
    private final Long views;
}