package ru.practicum.explorewithme.category.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class NewCategoryDto {

    @NotBlank
    @Length(max = 50)
    private String name;
}