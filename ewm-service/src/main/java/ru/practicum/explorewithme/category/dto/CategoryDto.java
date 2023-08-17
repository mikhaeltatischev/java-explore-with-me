package ru.practicum.explorewithme.category.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public final class CategoryDto {

    private Long id;
    @NotBlank
    @NotNull
    @Length(max = 50)
    private String name;
}