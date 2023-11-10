package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final FilmService filmService;

    @GetMapping
    public List<Mpa> getAll() {
        log.debug("[getAll] Start.");
        return filmService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getById(@PathVariable("id") Integer mpaId) {
        log.debug("[getFilmById] Start.");
        return filmService.getMpaById(mpaId);
    }
}