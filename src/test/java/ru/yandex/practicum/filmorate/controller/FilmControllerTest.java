package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {

    private FilmController filmController;
    private Film film;

    @BeforeEach
    void init() {
        filmController = new FilmController();

        film = Film.builder()
                .name("Film1")
                .description("Description1")
                .releaseDate(LocalDate.of(2023, 9, 30))
                .duration(180)
                .build();
    }

    @Test
    public void create_CorrectFilm() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        filmController.add(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
        filmController.add(film);
        assertEquals(2, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void update_CorrectFilm() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        filmController.add(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
        film = filmController.get().get(0);
        film.setName("FilmNew");
        filmController.update(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
        assertEquals(film, filmController.get().get(0));

    }

    @Test
    public void create_FilmWithId() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        filmController.add(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
        assertThrows(ValidationException.class,
                () -> filmController.add(filmController.get().get(0)), "ожидался ValidationException");
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithBlankName() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setName("");
        assertThrows(ValidationException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithNullName() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setName(null);
        assertThrows(NullPointerException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithDescriptionLength200_Correct() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setDescription("a".repeat(200));
        filmController.add(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithDescription_Length201_Incorrect() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setDescription("a".repeat(201));
        assertThrows(ValidationException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithIncorrectReleaseDate() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithVeryOldReleaseDate() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        filmController.add(film);
        assertEquals(1, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithNegativeDuration() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setDuration(-1);
        assertThrows(ValidationException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void create_FilmWithZeroDuration() {
        assertEquals(0, filmController.get().size(), "Список фильмов должен быть пуст.");
        film.setDuration(0);
        assertThrows(ValidationException.class,
                () -> filmController.add(film), "ожидался ValidationException");
        assertEquals(0, filmController.get().size(), "Количество фильмов в списке некорректно.");
    }
}
