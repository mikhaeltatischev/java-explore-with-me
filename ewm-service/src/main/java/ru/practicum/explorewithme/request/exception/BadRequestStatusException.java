package ru.practicum.explorewithme.request.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequestStatusException extends RuntimeException {

    private static final String MESSAGE = "Status should be PENDING";

    public BadRequestStatusException() {
        super(MESSAGE);
        log.info(MESSAGE);
    }
}