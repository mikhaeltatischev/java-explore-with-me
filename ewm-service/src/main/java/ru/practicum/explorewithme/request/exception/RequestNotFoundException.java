package ru.practicum.explorewithme.request.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Participation request with id: %s not found";

    public RequestNotFoundException(long requestId) {
        super(String.format(MESSAGE, requestId));
        log.info(String.format(MESSAGE, requestId));
    }
}