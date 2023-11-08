package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class FilmGenreForeignKey implements Serializable {
    private Long filmId;
    private Integer genreId;
}
