package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    private Film film;

    @BeforeEach
    public void setup() {
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(1).build());
        film = Film.builder()
                .name("Film1")
                .description("Description1")
                .releaseDate(LocalDate.of(2023, 9, 30))
                .genres(genres)
                .mpa(Mpa.builder().id(1).build())
                .duration(180)
                .likes(new HashSet<>())
                .build();

    }

    @Test
    public void create_CorrectFilm() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        checkCorrectRequest(sendPost(film), film);
        film = film.toBuilder().id(1L).build();
        List<Film> filmList = sendGet();
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
        //assertEquals(film, sendGet().get(0), "Вернулся некорректный фильм.");
    }

    @Test
    public void update_CorrectFilm() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        sendPost(film);
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
        film = film.toBuilder().id(1L).name("FilmNew").build();
        checkCorrectRequest(sendPut(film), film);
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
        assertEquals(film.getName(), sendGet().get(0).getName());
    }


    @Test
    public void validateName() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        film.setName("");
        checkBadRequest(sendPost(film));
        film.setName(null);
        checkBadRequest(sendPost(film));
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");

    }

    @Test
    public void validateDescription() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        film.setDescription("a".repeat(200));
        checkCorrectRequest(sendPost(film), film);
        film.setDescription("a".repeat(201));
        checkBadRequest(sendPost(film));
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void validateReleaseDate() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        checkBadRequest(sendPost(film));
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        checkCorrectRequest(sendPost(film), film);
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
    }

    @Test
    public void validateDuration() throws Exception {
        assertEquals(0, sendGet().size(), "Список фильмов должен быть пуст.");
        film.setDuration(-1);
        checkBadRequest(sendPost(film));
        film.setDuration(0);
        checkBadRequest(sendPost(film));
        film.setDuration(1);
        checkCorrectRequest(sendPost(film), film);
        assertEquals(1, sendGet().size(), "Количество фильмов в списке некорректно.");
    }


    private List<Film> sendGet() throws Exception {
        MvcResult result = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return Arrays.asList(objectMapper.readValue(json, Film[].class));
    }

    private ResultActions sendPost(Film film) throws Exception {
        return mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)));
    }

    private ResultActions sendPut(Film film) throws Exception {
        return mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)));
    }

    private void checkCorrectRequest(ResultActions resultActions, Film film) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.name")).value(film.getName()))
                .andExpect(jsonPath(("$.description")).value(film.getDescription()))
                .andExpect(jsonPath(("$.releaseDate"))
                        .value(film.getReleaseDate().toString()))
                .andExpect(jsonPath(("$.duration")).value(film.getDuration()));

    }

    private void checkBadRequest(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(status().isBadRequest());
    }
}
