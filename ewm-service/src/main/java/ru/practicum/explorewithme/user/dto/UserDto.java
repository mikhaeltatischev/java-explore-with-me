package ru.practicum.explorewithme.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public final class UserDto {

    private final Long id;
    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    private final String email;
    @NotBlank
    @Length(min = 2, max = 250)
    private final String name;
}