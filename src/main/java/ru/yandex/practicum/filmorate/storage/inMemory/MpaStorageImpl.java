package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.impl.AbstractStorageImpl;


@Component
public class MpaStorageImpl extends AbstractStorageImpl<Mpa, Integer> implements MpaStorage {
}
