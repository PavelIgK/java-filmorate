package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Data
public class FilmGenreForeignKey implements Serializable {
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "genre_id")
    private Long genreId;
}
