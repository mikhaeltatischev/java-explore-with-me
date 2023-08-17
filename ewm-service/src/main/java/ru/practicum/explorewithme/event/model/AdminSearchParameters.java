package ru.practicum.explorewithme.event.model;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AdminSearchParameters {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<Long> users;
    private final List<String> states;
    private final List<Long> categories;
    private final LocalDateTime rangeStart;
    private final LocalDateTime rangeEnd;
    private final PageRequest pageRequest;

    public static AdminSearchParameters of(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                           String rangeEnd, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);

        LocalDateTime start;
        LocalDateTime end;

        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, FORMATTER);
        } else {
            start = LocalDateTime.now();
        }
        if (rangeEnd != null) {
            end = LocalDateTime.parse(rangeEnd, FORMATTER);
        } else {
            end = LocalDateTime.now().plusYears(2);
        }

        return AdminSearchParameters.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(start)
                .rangeEnd(end)
                .pageRequest(pageRequest)
                .build();
    }
}