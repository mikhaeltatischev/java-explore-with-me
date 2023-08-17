package ru.practicum.explorewithme.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.explorewithme.common.FieldIsNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PublicSearchParameters {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String text;
    private final List<Long> categories;
    private final Boolean paid;
    private final LocalDateTime rangeStart;
    private final LocalDateTime rangeEnd;
    private final Boolean onlyAvailable;
    private final PageRequest pageRequest;

    public static PublicSearchParameters of(String text, List<Long> categories, Boolean paid, String start,
                                            String end, boolean onlyAvailable, String sort, int from, int size) {
        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;
        String lowerCaseText = null;
        PageRequest pageRequest;
        String rightSort;

        if (sort != null) {
            if (sort.equals("EVENT_DATE")) {
                rightSort = "eventDate";
            } else {
                rightSort = "views";
            }
            pageRequest = PageRequest.of(from / size, size, Sort.by(rightSort));
        } else {
            pageRequest = PageRequest.of(from / size, size);
        }
        if (text != null) {
            lowerCaseText = text.toLowerCase();
        }
        if (start != null) {
            rangeStart = LocalDateTime.parse(start, FORMATTER);
        } else {
            rangeStart = LocalDateTime.now();
        }
        if (end != null) {
            rangeEnd = LocalDateTime.parse(end, FORMATTER);
            if (rangeEnd.isBefore(rangeStart)) {
                throw new FieldIsNotValidException("RangeEnd, RangeStart");
            }
        } else {
            rangeEnd = LocalDateTime.now().plusYears(2);
        }

        return PublicSearchParameters.builder()
                .text(lowerCaseText)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .pageRequest(pageRequest)
                .build();
    }
}