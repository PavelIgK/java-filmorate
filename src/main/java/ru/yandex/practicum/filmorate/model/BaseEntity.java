package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Сущность для реализаций объектов.
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class BaseEntity<T> {
    private T id;
}
