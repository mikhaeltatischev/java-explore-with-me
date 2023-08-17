package ru.practicum.explorewithme.event.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventBadTimeException extends RuntimeException {

    private static final String MESSAGE = "Date and time for which the event is scheduled cannot be earlier" +
            " than %s hours from the current moment";

    public EventBadTimeException(int hours) {
        super(String.format(MESSAGE, hours));
        log.info(String.format(MESSAGE, hours));
    }
}