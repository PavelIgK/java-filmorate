package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;


@Component
public class MpaStorageImpl extends AbstractStorageImpl<Mpa, Integer> implements MpaStorage {
}
