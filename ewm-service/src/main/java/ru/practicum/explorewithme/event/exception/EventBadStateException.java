package ru.practicum.explorewithme.event.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventBadStateException extends RuntimeException {

    private static final String MESSAGE = "Event status should be: %s";

    public EventBadStateException(String message) {
        super(String.format(MESSAGE, message));
        log.info(String.format(MESSAGE, message));
    }
}