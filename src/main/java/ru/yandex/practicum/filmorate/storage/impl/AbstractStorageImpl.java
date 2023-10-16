package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Entity;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Slf4j

public abstract class AbstractStorageImpl<T extends Entity> implements Storage<T> {

    private final Map<Integer, T> storage = new HashMap<>();
    int id = 0;

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T add(T t) {
        if (t.getId() != 0) {
            throw new ValidationException("В метод создания пришла сущность с id");
        }

        log.debug("Сущность для добавления: {}.", t);
        //noinspection unchecked
        t = (T) t.toBuilder().id(++id).build();
        storage.put(t.getId(), t);
        log.debug("Добавлена новая сущность. {}.", t);
        return t;
    }

    @Override
    public T update(T t) {
        log.debug("Обновляем {}", t);
        if (storage.containsKey(t.getId())) {
            storage.put(t.getId(), t);
            log.debug("Обновлено. Текущее состояние {}.", t);
        } else {
            throw new NotFoundException("Сущности с такими id нет");
        }
        return t;
    }

    @Override
    public T getById(int id) {
        log.debug("Запрос получения сущности с id = {}.", id);
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Сущность не найдена.");
        }
        return storage.get(id);
    }
}
