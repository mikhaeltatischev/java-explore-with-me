package ru.practicum.explorewithme.location.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocationsFieldsIsEmptyException extends RuntimeException {

    private static final String MESSAGE = "Location`s fields is empty";

    public LocationsFieldsIsEmptyException() {
        super(MESSAGE);
        log.info(MESSAGE);
    }
}