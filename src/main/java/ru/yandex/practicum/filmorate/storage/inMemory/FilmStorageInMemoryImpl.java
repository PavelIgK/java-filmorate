package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.AbstractStorageImpl;

@Slf4j
@Component
public class FilmStorageInMemoryImpl extends AbstractStorageImpl<Film, Long> implements FilmStorage {
}
