package ru.practicum.explorewithme.compilation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompilationNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Compilation with id: %s not found";

    public CompilationNotFoundException(long compId) {
        super(String.format(MESSAGE, compId));
        log.info(String.format(MESSAGE, compId));
    }
}