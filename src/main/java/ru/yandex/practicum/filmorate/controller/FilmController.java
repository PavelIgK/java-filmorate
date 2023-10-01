package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.validate.FilmValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmValidation filmValidate = new FilmValidation();
    protected final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        filmValidate.validate(film);
        if (film.getId() != 0) {
            throw new ValidationException("В метод создания пришел фильм с id");
        }
        film = film.toBuilder().id(++id).build();
        films.put(film.getId(), film);
        log.debug("Добавлен новый фильм. " + film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmValidate.validate(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Обновлен фильм." + film);
        } else {
            throw new ValidationException("Фильма с такими id нет");
        }

        return film;
    }

}
