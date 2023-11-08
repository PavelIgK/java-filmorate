package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final FilmService filmService;

    @GetMapping
    public List<Genre> getAll() {
        log.debug("[getAll] Start.");
        return filmService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable("id") Integer genreId) {
        log.debug("[getById] Start.");
        return filmService.getGenreById(genreId);
    }
}