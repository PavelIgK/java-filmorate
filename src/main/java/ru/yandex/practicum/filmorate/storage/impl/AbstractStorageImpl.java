package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Slf4j

public abstract class AbstractStorageImpl<T extends BaseEntity<S>, S> implements Storage<T, S> {

    private final Map<S, T> storage = new HashMap<>();

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T add(T t) {
        log.debug("Сущность для добавления: {}.", t);
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
    public Optional<T> findById(S id) {
        log.debug("Запрос получения сущности с id = {}.", id);
        if (!storage.containsKey(id)) {
            throw new NotFoundException("Сущность не найдена.");
        }
        return Optional.ofNullable(storage.get(id));
    }
}
