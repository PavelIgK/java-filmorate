package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
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
}
