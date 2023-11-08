package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Сущность для реализаций объектов.
 */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
public abstract class BaseEntity<T> {
    private T id;
}
