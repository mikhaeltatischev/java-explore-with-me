package ru.practicum.explorewithme.location.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocationNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Location with id: %s not found";

    public LocationNotFoundException(long id) {
        super(String.format(MESSAGE, id));
        log.info(String.format(MESSAGE, id));
    }
}