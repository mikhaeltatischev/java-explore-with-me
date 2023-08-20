package ru.practicum.explorewithme.event.dto;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Status;
import ru.practicum.explorewithme.location.model.Location;
import ru.practicum.explorewithme.user.dto.UserMapper;
import ru.practicum.explorewithme.user.dto.UserShortDto;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.location.dto.LocationMapper.toDto;

public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventFullDto toFullDto(Event event) {
        String publishedOn = null;

        if (event.getPublishedOn() != null) {
            publishedOn = event.getPublishedOn().format(FORMATTER);
        }

        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .location(toDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(publishedOn)
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static List<EventFullDto> toFullDto(List<Event> events) {
        return events.stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    public static EventShortDto toShortDto(Event event) {
        Long id = event.getId();
        String annotation = event.getAnnotation();
        CategoryDto category = CategoryMapper.toDto(event.getCategory());
        Long confirmedRequests = event.getConfirmedRequests();
        String eventDate = event.getEventDate().format(FORMATTER);
        UserShortDto initiator = UserMapper.toShortDto(event.getInitiator());
        Boolean paid = event.getPaid();
        String title = event.getTitle();
        Long views = event.getViews();

        return new EventShortDto(id, annotation, category, confirmedRequests, eventDate, initiator, paid, title, views);
    }

    public static List<EventShortDto> toShortDto(List<Event> events) {
        return events.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public static Event toEvent(NewEventDto event, User user, Category category, Location location) {
        boolean paid;
        int limit;
        boolean requestModeration;

        if (event.getPaid() == null) {
            paid = false;
        } else {
            paid = event.getPaid();
        }

        if (event.getParticipantLimit() == null) {
            limit = 0;
        } else {
            limit = event.getParticipantLimit();
        }

        if (event.getRequestModeration() == null) {
            requestModeration = true;
        } else  {
            requestModeration = event.getRequestModeration();
        }

        return Event.builder()
                .location(location)
                .annotation(event.getAnnotation())
                .category(category)
                .description(event.getDescription())
                .eventDate(LocalDateTime.parse(event.getEventDate(), FORMATTER))
                .paid(paid)
                .participantLimit(limit)
                .requestModeration(requestModeration)
                .title(event.getTitle())
                .initiator(user)
                .state(Status.PENDING)
                .createdOn(LocalDateTime.now())
                .confirmedRequests(0L)
                .views(0L)
                .build();
    }
}