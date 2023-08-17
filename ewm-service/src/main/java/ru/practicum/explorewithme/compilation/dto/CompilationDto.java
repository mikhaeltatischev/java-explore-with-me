package ru.practicum.explorewithme.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.event.dto.EventShortDto;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public final class CompilationDto {

    private final Long id;
    private final List<EventShortDto> events;
    private final boolean pinned;
    @Length(max = 50)
    private final String title;
}