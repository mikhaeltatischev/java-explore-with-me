package ru.practicum.explorewithme.request.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicateRequestException extends RuntimeException {

    private static final String MESSAGE = "User already has request for event with id: %s";

    public DuplicateRequestException(long eventId) {
        super(String.format(MESSAGE, eventId));
        log.info(String.format(MESSAGE, eventId));
    }
}