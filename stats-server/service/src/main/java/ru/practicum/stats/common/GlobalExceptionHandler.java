package ru.practicum.stats.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.stats.exception.FieldIsNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleFieldIsNotValidException(final FieldIsNotValidException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        log.info(errors + "MESSAGE: " + e.getMessage());

        return new ApiError(errors, e.getMessage(), e.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleRuntimeException(final RuntimeException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        log.info(errors + "MESSAGE: " + e.getMessage());

        return new ApiError(errors, e.getMessage(), e.getLocalizedMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now().format(FORMATTER));
    }
}