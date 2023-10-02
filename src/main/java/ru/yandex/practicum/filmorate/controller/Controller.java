package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация хранения и работы с сущностями.
 * ПОка хранится в мапе которая тут же и создается.
 * @param <T> наследник сущности.
 */
@Slf4j
public abstract class Controller<T extends Entity> {

    protected final Map<Integer, T> maps = new HashMap<>();
    private int id = 0;

    /**
     * Получим все текущие сущности.
     * @return List<T>
     */
    public List<T> get() {
        return new ArrayList<>(maps.values());
    }

    /**
     * Добавим сущность если она валидна.
     *
     * @param entity входящая сущность.
     * @return добавленная сущность.
     */
    public T add(T entity) {
        validate(entity);
        if (entity.getId() != 0) {
            throw new ValidationException("В метод создания пришла сущность с id");
        }

        entity = (T) entity.toBuilder().id(++id).build();
        maps.put(entity.getId(), entity);
        log.debug("Добавлена новая сущность. " + entity);
        return entity;
    }

    /**
     * Обновим сущность если она валидна.
     * @param entity сущность.
     * @return обновленная сущность.
     */
    public T update(T entity) {
        validate(entity);
        if (maps.containsKey(entity.getId())) {
            maps.put(entity.getId(), entity);
            log.debug("Обновлен сущность." + entity);
        } else {
            throw new ValidationException("Сущности с такими id нет");
        }

        return entity;
    }

    /**
     * Метод валидации сущности.
     * @param entity сущность.
     */
    protected void validate(T entity) {
        log.debug("Validate in controller.");
    }
}
