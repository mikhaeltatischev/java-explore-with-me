package ru.practicum.explorewithme.event.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotInitiatorException extends RuntimeException {

    private static final String MESSAGE = "User with id: %s is not initiator of event with id: %s";

    public NotInitiatorException(long userId, long eventId) {
        super(String.format(MESSAGE, userId, eventId));
        log.info(String.format(MESSAGE, userId, eventId));
    }
}