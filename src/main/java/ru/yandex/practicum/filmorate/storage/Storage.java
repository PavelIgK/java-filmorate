package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.List;

public interface Storage<T extends Entity> {
    List<T> getAll();

    T add(T entity);

    T update(T entity);

    T getById(int id);
}
