package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Структура пользователя.
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User {

    @Setter(AccessLevel.PRIVATE)
    private int id;

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

    private Set<Integer> friends;
}
