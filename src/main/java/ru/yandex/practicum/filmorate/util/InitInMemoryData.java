package ru.yandex.practicum.filmorate.util;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;


@RequiredArgsConstructor
@Configuration
public class InitInMemoryData {
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;

    @Autowired
    public void initMpa() {
        mpaStorage.add(Mpa.builder()
                .id(1)
                .name("G")
                .description("у фильма нет возрастных ограничений")
                .build());

        mpaStorage.add(Mpa.builder()
                .id(2)
                .name("PG")
                .description("детям рекомендуется смотреть фильм с родителями")
                .build());

        mpaStorage.add(Mpa.builder()
                .id(3)
                .name("PG-13")
                .description("детям до 13 лет просмотр не желателен")
                .build());

        mpaStorage.add(Mpa.builder()
                .id(4)
                .name("R")
                .description("лицам до 17 лет просматривать фильм можно только в присутствии взрослого")
                .build());

        mpaStorage.add(Mpa.builder()
                .id(5)
                .name("NC-17")
                .description("лицам до 18 лет просмотр запрещён")
                .build());

    }

    @Autowired
    public void initGenres() {

        genreStorage.add(Genre.builder()
                .id(1)
                .name("Комедия")
                .build());

        genreStorage.add(Genre.builder()
                .id(2)
                .name("Драма")
                .build());

        genreStorage.add(Genre.builder()
                .id(3)
                .name("Мультфильм")
                .build());

        genreStorage.add(Genre.builder()
                .id(4)
                .name("Триллер")
                .build());

        genreStorage.add(Genre.builder()
                .id(5)
                .name("Документальный")
                .build());

        genreStorage.add(Genre.builder()
                .id(6)
                .name("Боевик")
                .build());

    }

}
