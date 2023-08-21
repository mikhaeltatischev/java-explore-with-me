package ru.practicum.explorewithme.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.exception.CategoryNotFoundException;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.event.dto.*;
import ru.practicum.explorewithme.event.exception.EventBadStateException;
import ru.practicum.explorewithme.event.exception.EventBadTimeException;
import ru.practicum.explorewithme.event.exception.EventNotFoundException;
import ru.practicum.explorewithme.event.exception.NotInitiatorException;
import ru.practicum.explorewithme.event.model.*;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.location.exception.LocationsFieldsIsEmptyException;
import ru.practicum.explorewithme.location.model.Location;
import ru.practicum.explorewithme.location.repository.LocationRepository;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.exception.ParticipantLimitException;
import ru.practicum.explorewithme.request.model.ParticipationRequest;
import ru.practicum.explorewithme.request.repository.ParticipationRequestRepository;
import ru.practicum.explorewithme.user.exception.UserNotFoundException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.event.dto.EventMapper.*;
import static ru.practicum.explorewithme.request.dto.RequestMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String APP = "ewm-service";

    private final StatsClient client;
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ParticipationRequestRepository requestRepository;

    @Override
    public EventFullDto findEventPublic(long eventId, HttpServletRequest request) {
        Event event = findEvent(eventId);

        if (event.getState() != Status.PUBLISHED) {
            throw new EventNotFoundException(eventId);
        }

        Boolean unique = client.checkUnique(request.getRequestURI(), request.getRemoteAddr());

        if (unique) {
            event.setViews(event.getViews() + 1);
            updateEvent(event);
        }

        client.addHit(APP, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(FORMATTER));

        log.info("Found event: " + event);

        return toFullDto(event);
    }

    @Override
    public List<EventShortDto> findEvents(long userId, int from, int size) {
        findUser(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);

        log.info("Found events: " + events);

        return toShortDto(events);
    }

    @Override
    public List<EventShortDto> findEvents(PublicSearchParameters parameters, HttpServletRequest request) {
        List<Category> categories = findCategories(parameters.getCategories());
        List<Event> foundEvents;
        client.addHit(APP, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now().format(FORMATTER));

        if (parameters.getText() != null && parameters.getPaid() != null && categories != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getText(), parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getText() != null && parameters.getPaid() != null && categories != null) {
            foundEvents = repository.findAll(parameters.getText(), parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getText() != null && parameters.getPaid() != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getText(), parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (parameters.getText() != null && categories != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getText(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getPaid() != null && categories != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getText() != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getText(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (parameters.getPaid() != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (categories != null && parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getRangeStart(), parameters.getRangeEnd(),
                    categories, parameters.getPageRequest());
        } else if (parameters.getText() != null && parameters.getPaid() != null) {
            foundEvents = repository.findAll(parameters.getText(), parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (parameters.getText() != null && categories != null) {
            foundEvents = repository.findAll(parameters.getText(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getPaid() != null && categories != null) {
            foundEvents = repository.findAll(parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), categories, parameters.getPageRequest());
        } else if (parameters.getText() != null) {
            foundEvents = repository.findAll(parameters.getText(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (parameters.getPaid() != null) {
            foundEvents = repository.findAll(parameters.getPaid(), parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (categories != null) {
            foundEvents = repository.findAll(parameters.getRangeStart(), parameters.getRangeEnd(),
                    categories, parameters.getPageRequest());
        } else if (parameters.getOnlyAvailable()) {
            foundEvents = repository.findAllOnlyAvailable(parameters.getRangeStart(), parameters.getRangeEnd(),
                    parameters.getPageRequest());
        } else {
            foundEvents = repository.findAll(parameters.getRangeStart(), parameters.getRangeEnd(),
                    parameters.getPageRequest());
        }

        log.info("Found events: " + foundEvents);

        return toShortDto(foundEvents);
    }

    @Override
    public List<EventFullDto> findEventsForAdmin(AdminSearchParameters parameters) {
        List<Event> foundEvents;
        List<Category> categories = findCategories(parameters.getCategories());
        List<User> users = findUsers(parameters.getUsers());
        List<Status> statuses = findStates(parameters.getStates());

        if (users != null && statuses != null && categories != null) {
            foundEvents = repository.findAllByInitiatorInAndStateInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(users,
                    statuses, categories, parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (users != null && statuses != null) {
            foundEvents = repository.findAllByInitiatorInAndStateInAndEventDateIsAfterAndEventDateIsBefore(users,
                    statuses, parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (users != null && categories != null) {
            foundEvents = repository.findAllByInitiatorInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(users,
                    categories, parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (statuses != null && categories != null) {
            foundEvents = repository.findAllByStateInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(statuses,
                    categories, parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (users != null) {
            foundEvents = repository.findAllByInitiatorInAndEventDateIsAfterAndEventDateIsBefore(users,
                    parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (statuses != null) {
            foundEvents = repository.findAllByStateInAndEventDateIsAfterAndEventDateIsBefore(statuses,
                    parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else if (categories != null) {
            foundEvents = repository.findAllByCategoryInAndEventDateIsAfterAndEventDateIsBefore(categories,
                    parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        } else {
            foundEvents = repository.findAllByEventDateIsAfterAndEventDateIsBefore(parameters.getRangeStart(),
                    parameters.getRangeEnd(), parameters.getPageRequest());
        }

        log.info("Found events: " + foundEvents);

        return toFullDto(foundEvents);
    }

    @Override
    public List<EventShortDto> findEventsByLocation(LocationDto location) {
        List<Event> events;

        if (location.getAddress() != null && location.getName() != null && location.getLat() != null && location.getLon() != null) {
            events = repository.findAllByLocationNameIgnoreCaseAndLocationAddressIgnoreCaseAndLocationLatAndLocationLon(location.getName(),
                    location.getAddress(), location.getLat(), location.getLon());
        } else if (location.getAddress() != null && location.getName() != null) {
            events = repository.findAllByLocationAddressAndLocationName(location.getName(), location.getAddress());
        } else if (location.getLat() != null && location.getLon() != null) {
            events = repository.findAllByLocationLatAndLocationLon(location.getLat(), location.getLon());
        } else if (location.getAddress() != null) {
            events = repository.findAllByLocationAddressIgnoreCase(location.getAddress());
        } else if (location.getName() != null) {
            events = repository.findAllByLocationNameIgnoreCase(location.getName());
        } else {
            throw new LocationsFieldsIsEmptyException();
        }

        log.info("Found events: " + events);

        return toShortDto(events);
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        findUser(userId);
        Event event = findEvent(eventId);

        log.info("Found event: " + event);

        return toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEvent) {
        User user = findUser(userId);
        Category category = findCategory(newEvent.getCategory());
        Location location = findOrCreateLocation(newEvent.getLocation());
        checkValidEventDate(newEvent.getEventDate());
        Event savedEvent = repository.save(toEvent(newEvent, user, category, location));

        log.info("Event: " + savedEvent + " saved");

        return toFullDto(savedEvent);
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventUserRequest request) {
        Event event = findEvent(eventId);
        findUser(userId);
        checkValidUser(userId, event);
        checkEventStatus(event);
        checkValidEventDate(request.getEventDate());
        updateEventFields(request, event);
        Event updatedEvent = repository.save(event);

        log.info("Event: " + updatedEvent + " updated");

        return toFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequest(long userId, long eventId) {
        List<ParticipationRequest> requests = requestRepository.findAllByEventInitiatorIdAndEventId(userId, eventId);

        log.info("Found requests: " + requests);

        return toDto(requests);
    }

    @Override
    public EventRequestStatusUpdateResult changeRequestsStatus(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) {
        findUser(userId);
        Event event = findEvent(eventId);
        List<ParticipationRequest> requests = requestRepository.findAllById(updateRequest.getRequestIds());

        if (!event.getRequestModeration()) {
            checkParticipantLimit(event);
            requests.forEach(request -> {
                request.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            });
            updateEvent(event);
            return new EventRequestStatusUpdateResult(toDto(requests), new ArrayList<>());
        }

        List<ParticipationRequest> confirmedRequests = new ArrayList<>();
        List<ParticipationRequest> rejectedRequests = new ArrayList<>();
        Status status = Status.valueOf(updateRequest.getStatus());

        requests.forEach(request -> {
            if (status == Status.CONFIRMED) {
                checkParticipantLimit(event);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                request.setStatus(Status.CONFIRMED);
                confirmedRequests.add(request);
            } else {
                checkParticipantLimit(event);
                request.setStatus(Status.REJECTED);
                rejectedRequests.add(request);
            }
        });

        updateEvent(event);

        return new EventRequestStatusUpdateResult(toDto(confirmedRequests), toDto(rejectedRequests));
    }

    @Override
    public EventFullDto editEvent(long eventId, UpdateEventAdminRequest request) {
        Event event = findEvent(eventId);
        checkValidEventDateForAdmin(request.getEventDate());
        checkEventStatusForAdmin(request, event);
        updateEventFields(request, event);
        Event updatedEvent = repository.save(event);

        log.info("Event: " + updatedEvent + " updated");

        return toFullDto(updatedEvent);
    }

    private List<Status> findStates(List<String> states) {
        List<Status> statuses;

        if (states != null) {
            statuses = states.stream()
                    .map(Status::valueOf)
                    .collect(Collectors.toList());
        } else {
            statuses = null;
        }
        return statuses;
    }

    private List<Category> findCategories(List<Long> catIds) {
        List<Category> categories;

        if (catIds != null) {
            categories = categoryRepository.findAllById(catIds);
            if (categories.size() == 0) {
                categories = null;
            }
        } else {
            categories = null;
        }
        return categories;
    }

    private List<User> findUsers(List<Long> userIds) {
        List<User> users;

        if (userIds != null) {
            users = userRepository.findAllById(userIds);
        } else {
            users = null;
        }
        return users;
    }

    private void checkEventStatusForAdmin(UpdateEventAdminRequest request, Event event) {
        if (request.getStateAction() != null) {
            if (StateAction.PUBLISH_EVENT == StateAction.valueOf(request.getStateAction())) {
                if (event.getState() != Status.PENDING) {
                    throw new EventBadStateException("PENDING");
                }
            }
            if (StateAction.REJECT_EVENT == StateAction.valueOf(request.getStateAction())) {
                if (event.getState() == Status.PUBLISHED) {
                    throw new EventBadStateException("PENDING, REJECTED");
                }
            }
        }
    }

    private User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Category findCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private Event findEvent(long eventId) {
        return repository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    private void checkValidUser(long userId, Event event) {
        if (event.getInitiator().getId() != userId) {
            throw new NotInitiatorException(userId, event.getId());
        }
    }

    private void checkEventStatus(Event event) {
        if (event.getState() == Status.PUBLISHED) {
            throw new EventBadStateException("PENDING or REJECTED");
        }
    }

    private void checkValidEventDate(String eventDate) {
        if (eventDate != null) {
            if (LocalDateTime.parse(eventDate, FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
                throw new EventBadTimeException(2);
            }
        }
    }

    private void checkValidEventDateForAdmin(String eventDate) {
        if (eventDate != null) {
            if (LocalDateTime.parse(eventDate, FORMATTER).isBefore(LocalDateTime.now().plusHours(1))) {
                throw new EventBadTimeException(1);
            }
        }
    }


    private void checkParticipantLimit(Event event) {
        if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ParticipantLimitException();
        }
    }

    private Location findOrCreateLocation(Location location) {
        Location foundLocation = locationRepository.checkLocation(location.getLat(), location.getLon());

        if (foundLocation == null) {
            foundLocation = locationRepository.save(location);
        }

        return foundLocation;
    }

    private void updateEvent(Event event) {
        repository.save(event);
    }

    private void updateEventFields(UpdateEvent request, Event event) {
        String annotation = request.getAnnotation();
        Long category = request.getCategory();
        String description = request.getDescription();
        String eventDate = request.getEventDate();
        Location location = findOrCreateLocation(event.getLocation());
        Boolean paid = request.getPaid();
        Integer participantLimit = request.getParticipantLimit();
        Boolean requestModeration = request.getRequestModeration();
        String title = request.getTitle();

        event.setLocation(location);
        if (annotation != null) {
            event.setAnnotation(annotation);
        }
        if (category != null) {
            event.setCategory(findCategory(category));
        }
        if (description != null) {
            event.setDescription(description);
        }
        if (eventDate != null) {
            event.setEventDate(LocalDateTime.parse(eventDate, FORMATTER));
        }
        if (paid != null) {
            event.setPaid(paid);
        }
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }
        if (request.getStateAction() != null) {
            StateAction stateAction = StateAction.valueOf(request.getStateAction());
            if (stateAction == StateAction.SEND_TO_REVIEW) {
                event.setState(Status.PENDING);
            } else if (stateAction == StateAction.CANCEL_REVIEW) {
                event.setState(Status.CANCELED);
            } else if (stateAction == StateAction.PUBLISH_EVENT) {
                event.setState(Status.PUBLISHED);
            } else {
                event.setState(Status.REJECTED);
            }
        }
        if (title != null) {
            event.setTitle(title);
        }
    }
}