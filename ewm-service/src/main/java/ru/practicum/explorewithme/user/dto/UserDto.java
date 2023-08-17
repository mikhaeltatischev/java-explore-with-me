package ru.practicum.explorewithme.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public final class UserDto {

    private Long id;
    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    private String email;
    @NotBlank
    @Length(min = 2, max = 250)
    private String name;
}