package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.validate.FilmValidation;

import javax.validation.Valid;
import java.util.List;

/**
 * Фильмы.
 * Обработчик запросов по эндпоинту /films.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final FilmValidation filmValidate = new FilmValidation();

    /**
     * Получаем все фильмы.
     * @return Список фильмов.
     */
    @Override
    @GetMapping()
    public List<Film> get() {
        return super.get();
    }

    /**
     * Добавляем фильм если он валиден.
     * @param film входящая сущность.
     * @return добавленный фильм.
     */
    @Override
    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        return super.add(film);
    }

    /**
     * Обновляем фильм если он валиден.
     * @param film сущность.
     * @return обновленный фильм.
     */
    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return super.update(film);
    }

    /**
     * Валидируем фильм.
     * @param film сущность.
     */
    @Override
    protected void validate(Film film) {
        log.debug("Validate in film");
        filmValidate.isValid(film);
    }
}
