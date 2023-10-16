package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getById(filmId);
    }

    public void addLike(int filmId, int userId) {
        userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        film.getLikes().add(userId);
        film.setRate(film.getRate() + 1);
        log.debug("Лайк от пользователя с id {} добавлен к фильму с id {} ", userId, filmId);
    }

    public void removeLike(int filmId, int userId) {
        userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        boolean likeDeleted = film.getLikes().remove(userId);
        if (likeDeleted) {
            film.setRate(film.getRate() - 1);
            log.debug("Лайк от пользователя с id {} удален у фильма с id {} ", userId, filmId);
        } else {
            log.debug("Лайк от пользователя с id {} не найден у фильма с id {} ", userId, filmId);
        }
    }

    public List<Film> getPopularFilm(int count) {
        List<Film> filmList = filmStorage.getAll();

        return filmList.stream()
                .sorted(Comparator.comparingInt(Film::getRate).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
