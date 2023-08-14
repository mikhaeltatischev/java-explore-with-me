package ru.practicum.explorewithme.request.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestOwnerException extends RuntimeException {

    public RequestOwnerException(String message) {
        super(message);
        log.info(message);
    }
}