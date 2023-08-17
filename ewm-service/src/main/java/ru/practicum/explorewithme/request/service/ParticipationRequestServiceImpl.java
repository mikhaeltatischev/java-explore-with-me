package ru.practicum.explorewithme.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.event.exception.EventIsNotPublishedException;
import ru.practicum.explorewithme.event.exception.EventNotFoundException;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Status;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.exception.DuplicateRequestException;
import ru.practicum.explorewithme.request.exception.ParticipantLimitException;
import ru.practicum.explorewithme.request.exception.RequestNotFoundException;
import ru.practicum.explorewithme.request.exception.RequestOwnerException;
import ru.practicum.explorewithme.request.model.ParticipationRequest;
import ru.practicum.explorewithme.request.repository.ParticipationRequestRepository;
import ru.practicum.explorewithme.user.exception.UserNotFoundException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.explorewithme.request.dto.RequestMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId) {
        List<ParticipationRequest> requests = repository.findAllByRequesterId(userId);

        log.info("Found requests: " + requests);

        return toDto(requests);
    }

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        Event event = findEvent(eventId);
        User user = findUser(userId);
        checkDuplicateRequest(userId, eventId);
        checkOwnerOfEvent(userId, event);
        checkPublishedEvent(event);
        Status status;

        if (checkParticipationLimit(event) || !event.getRequestModeration()) {
            status = Status.CONFIRMED;
            addConfirmedRequestForEvent(event);
        } else {
            status = Status.PENDING;
        }

        ParticipationRequest newRequest = repository.save(ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(status)
                .build());

        log.info("Saved request: " + newRequest);

        return toDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        findUser(userId);
        ParticipationRequest request = findRequest(requestId);
        checkOwner(userId, request);

        request.setStatus(Status.CANCELED);
        repository.save(request);

        log.info("Request: " + request + " canceled");

        return toDto(request);
    }

    private User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Event findEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    private ParticipationRequest findRequest(long requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(requestId));
    }

    private void checkDuplicateRequest(long userId, long eventId) {
        getRequests(userId).forEach(request -> {
            if (request.getEvent() == eventId) {
                throw new DuplicateRequestException(eventId);
            }
        });
    }

    private void checkOwnerOfEvent(long userId, Event event) {
        if (event.getInitiator().getId() == userId) {
            throw new RequestOwnerException("User with id: " + userId + " is owner event with id: " + event.getId());
        }
    }

    private void checkOwner(long userId, ParticipationRequest request) {
        long requestId = request.getRequester().getId();
        if (requestId != userId) {
            throw new RequestOwnerException("User with id: " + userId + " is not owner of request with id: " + requestId);
        }
    }

    private void checkPublishedEvent(Event event) {
        if (event.getState() != Status.PUBLISHED) {
            throw new EventIsNotPublishedException(event.getId());
        }
    }

    private boolean checkParticipationLimit(Event event) {
        if (event.getParticipantLimit() == 0) {
            return true;
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ParticipantLimitException();
        }
        return false;
    }

    private void addConfirmedRequestForEvent(Event event) {
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);
    }
}