package ru.practicum.explorewithme.event.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Event with id: %s not found";

    public EventNotFoundException(long id) {
        super(String.format(MESSAGE, id));
        log.info(String.format(MESSAGE, id));
    }
}