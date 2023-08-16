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

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private Long views;
}