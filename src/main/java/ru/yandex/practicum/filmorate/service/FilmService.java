package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    private Long id = 0L;

    public List<Film> getAll() {
        return filmStorage.findAll();
    }

    public Film add(Film film) {
        if (Optional.ofNullable(film.getId()).isPresent()) {
            throw new ValidationException("В метод создания пришел фильм с id");
        }

        Mpa currentMpa = mpaStorage.findById(film.getMpa().getId())
                .orElseThrow(() -> new NotFoundException("Нет рейтинга с таким id."));

        film.setGenres(film.getGenres()
                .stream().sorted()
                .peek(it -> {
                    it.setName(genreStorage.findById(it.getId()).get().getName());
                }).collect(Collectors.toSet()));

        film = film.toBuilder()
                .id(++id)
                .mpa(currentMpa)
                .build();
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (filmStorage.findById(film.getId()).isEmpty()) {
            throw new NotFoundException("Сущности с такими id нет");
        }
        Mpa currentMpa = mpaStorage.findById(film.getMpa().getId())
                .orElseThrow(() -> new NotFoundException("Нет рейтинга с таким id."));

        film.setGenres(film.getGenres()
                .stream().sorted()
                .peek(it -> {
                    it.setName(genreStorage.findById(it.getId()).get().getName());
                }).collect(Collectors.toSet()));

        film = film.toBuilder()
                .genres(film.getGenres())
                .mpa(currentMpa)
                .build();

        return filmStorage.update(film);
    }

    public Film getFilmById(Long filmId) {
        return filmStorage.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Нет фильма с id = " + filmId));
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + userId));
        ;
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Нет фильма с id = " + filmId));
        film.getLikes().add(userId);
        //film.setRate(film.getRate() + 1);
        filmStorage.update(film);
        log.debug("Лайк от пользователя с id {} добавлен к фильму с id {} ", userId, filmId);
    }

    public void removeLike(Long filmId, Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + filmId));
        ;
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Нет фильма с id = " + filmId));
        boolean likeDeleted = film.getLikes().remove(userId);
        if (likeDeleted) {
            film.setRate(film.getRate() - 1);
            log.debug("Лайк от пользователя с id {} удален у фильма с id {} ", userId, filmId);
        } else {
            log.debug("Лайк от пользователя с id {} не найден у фильма с id {} ", userId, filmId);
        }
        filmStorage.update(film);
    }

    public List<Film> getPopularFilm(int count) {
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size(),
                        Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Genre> getAllGenres() {
        return genreStorage.findAll();
    }

    public Genre getGenreById(Integer genreId) {
        return genreStorage.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Не найден жанр с id = " + genreId));
    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.findAll();
    }

    public Mpa getMpaById(Integer mpaId) {
        return mpaStorage.findById(mpaId)
                .orElseThrow(() -> new NotFoundException("Не найден жанр с id = " + mpaId));
    }
}
