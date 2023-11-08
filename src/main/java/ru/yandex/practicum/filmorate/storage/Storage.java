package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface Storage<T extends BaseEntity<S>, S> {
    List<T> getAll();

    T add(T entity);

    T update(T entity);

    Optional<T> getById(S id);
}
