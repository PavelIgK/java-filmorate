package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

/**
 * Фильмы.
 * Обработчик запросов по эндпоинту /films.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAll() {
        log.debug("[getAll] Start.");
        return filmService.getAll();
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.debug("[create] Start.");
        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody Film updatedFilm) {
        log.debug("[update] Start.");
        return filmService.update(updatedFilm);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable(value = "id") Long filmId) {
        log.debug("[getFilmById] Start.");
        return filmService.getFilmById(filmId);
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable(value = "id") Long filmId,
                        @PathVariable(value = "userId") Long userId) {
        log.debug("[addLike] Start.");
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable(value = "id") Long filmId,
                           @PathVariable(value = "userId") Long userId) {
        log.debug("[removeLike] Start.");
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(required = false, defaultValue = "10") int count) {
        log.debug("[getPopularFilm] Start.");
        return filmService.getPopularFilm(count);
    }
}
