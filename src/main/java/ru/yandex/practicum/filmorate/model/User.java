package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Структура пользователя.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User extends Entity {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^\\S+$",
            message = "Логин содержит пробел."
    )

    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;
}
