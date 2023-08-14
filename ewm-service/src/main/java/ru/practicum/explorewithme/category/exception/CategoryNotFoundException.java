package ru.practicum.explorewithme.category.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Category with id: %s not found";

    public CategoryNotFoundException(long id) {
        super(String.format(MESSAGE, id));
        log.info(String.format(MESSAGE, id));
    }
}