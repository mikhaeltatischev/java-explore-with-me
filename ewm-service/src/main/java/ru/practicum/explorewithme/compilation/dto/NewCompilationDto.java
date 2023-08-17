package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class NewCompilationDto {

    private final List<Long> events;
    private final boolean pinned;
    @NotBlank
    @Length(max = 50)
    private final String title;
}