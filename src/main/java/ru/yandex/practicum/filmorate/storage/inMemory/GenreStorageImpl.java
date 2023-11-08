package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.impl.AbstractStorageImpl;

@Component
public class GenreStorageImpl extends AbstractStorageImpl<Genre, Long> implements GenreStorage {
}
