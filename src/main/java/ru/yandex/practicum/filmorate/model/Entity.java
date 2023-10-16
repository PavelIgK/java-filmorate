package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Сущность для реализаций объектов.
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Entity {
    @Setter(AccessLevel.PRIVATE)
    private int id;
}
