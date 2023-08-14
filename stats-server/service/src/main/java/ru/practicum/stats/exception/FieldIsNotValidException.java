package ru.practicum.stats.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldIsNotValidException extends RuntimeException {

    private static final String MESSAGE = "Field: %s is not valid";

    public FieldIsNotValidException(String fields) {
        super(String.format(MESSAGE, fields));
        log.info(String.format(MESSAGE, fields));
    }
}