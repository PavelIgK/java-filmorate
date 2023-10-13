package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class FilmStorageInMemoryImpl implements FilmStorage {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    int id = 0;

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film add(Film film) {
        if (film.getId() != 0) {
            throw new ValidationException("В метод создания пришел фильм с id");
        }

        log.debug("Фильм для добавления: {}.", film);
        film = film.toBuilder().id(++id).likes(new HashSet<>()).build();
        filmMap.put(film.getId(), film);
        log.debug("Добавлен новый фильм. {}.", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        log.debug("Обновляем фильм {}", film);
        if (filmMap.containsKey(film.getId())) {
            film.toBuilder().likes(filmMap.get(film.getId()).getLikes()).build();
            filmMap.put(film.getId(), film);
            log.debug("Фильм обновлен. Текущее состояние {}.", film);
        } else {
            throw new NotFoundException("Фильма с такими id нет");
        }
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        log.debug("Запрос получения фильма с id = {}.", id);
        if (!filmMap.containsKey(id)) {
            throw new NotFoundException("Фильм не найден.");
        }
        return filmMap.get(id);
    }
}
