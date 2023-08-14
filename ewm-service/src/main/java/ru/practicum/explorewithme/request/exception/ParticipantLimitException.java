package ru.practicum.explorewithme.request.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParticipantLimitException extends RuntimeException {

    private static final String MESSAGE = "Number of requests exceeded";

    public ParticipantLimitException() {
        super(MESSAGE);
        log.info(MESSAGE);
    }
}