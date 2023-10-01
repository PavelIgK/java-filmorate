package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.util.validate.ReleaseDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {

    @Setter(AccessLevel.PRIVATE)
    private int id;

    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания - 200 символов.")
    private String description;

    @ReleaseDate(message = "Дата релиза слишком старая.")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private long duration;

}
