package ru.practicum.explorewithme.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.event.dto.EventShortDto;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public final class CompilationDto {

    private Long id;
    private List<EventShortDto> events;
    private boolean pinned;
    @Length(max = 50)
    private String title;
}