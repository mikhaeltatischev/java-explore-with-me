package ru.practicum.explorewithme.event.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventIsNotPublishedException extends RuntimeException {

    private static final String MESSAGE = "Event with id: %s is not published";

    public EventIsNotPublishedException(long eventId) {
        super(String.format(MESSAGE, eventId));
        log.info(String.format(MESSAGE, eventId));
    }
}