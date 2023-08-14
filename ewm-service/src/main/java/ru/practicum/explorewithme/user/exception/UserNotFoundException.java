package ru.practicum.explorewithme.user.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with id: %s not found";

    public UserNotFoundException(long userId) {
        super(String.format(MESSAGE, userId));
        log.info(String.format(MESSAGE, userId));
    }
}