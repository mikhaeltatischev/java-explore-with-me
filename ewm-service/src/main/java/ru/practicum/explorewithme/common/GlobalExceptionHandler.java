package ru.practicum.explorewithme.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.category.exception.CategoryNotFoundException;
import ru.practicum.explorewithme.compilation.exception.CompilationNotFoundException;
import ru.practicum.explorewithme.event.exception.EventBadStateException;
import ru.practicum.explorewithme.event.exception.EventBadTimeException;
import ru.practicum.explorewithme.event.exception.EventIsNotPublishedException;
import ru.practicum.explorewithme.event.exception.EventNotFoundException;
import ru.practicum.explorewithme.location.exception.LocationNotFoundException;
import ru.practicum.explorewithme.location.exception.LocationsFieldsIsEmptyException;
import ru.practicum.explorewithme.request.exception.DuplicateRequestException;
import ru.practicum.explorewithme.request.exception.ParticipantLimitException;
import ru.practicum.explorewithme.request.exception.RequestOwnerException;
import ru.practicum.explorewithme.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleRuntimeException(final RuntimeException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        log.info(errors + "MESSAGE: " + e.getMessage());

        return new ApiError(errors, e.getMessage(), "Runtime exception",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        log.info(errors + "MESSAGE: " + e.getMessage());

        return new ApiError(errors, e.getMessage(), "Not valid field",
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleFieldIsNotValidException(final FieldIsNotValidException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Field is not valid",
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleLocationsFieldsIsEmptyException(final LocationsFieldsIsEmptyException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Field is not valid",
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventBadStateException(final EventBadStateException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Inappropriate status",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEventNotFoundException(final EventNotFoundException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "The required object was not found",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUserNotFoundException(final UserNotFoundException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "The required object was not found",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCompilationNotFoundException(final CompilationNotFoundException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "The required object was not found",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCategoryNotFoundException(final CategoryNotFoundException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "The required object was not found",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleLocationNotFoundException(final LocationNotFoundException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "The required object was not found",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleParticipantLimitException(final ParticipantLimitException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Request limit exceeded",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateRequestException(final DuplicateRequestException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Repeated request",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleUserIsOwnerEventException(final RequestOwnerException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Wrong user",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventIsNotPublishedException(final EventIsNotPublishedException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Only published events can be changed",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataException(final DataIntegrityViolationException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Database error",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(FORMATTER));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleEventBadTimeException(final EventBadTimeException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        List<String> errors = new ArrayList<>();
        for (StackTraceElement stackTraceElement : stackTrace) {
            errors.add(stackTraceElement + "\n");
        }

        return new ApiError(errors, e.getMessage(), "Field 'time' is not valid",
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(FORMATTER));
    }
}