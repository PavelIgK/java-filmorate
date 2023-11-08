package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "film_genre")
@IdClass(FilmGenreForeignKey.class)
public class FilmGenre extends BaseEntity<Integer> {


    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
    private Genre genre;

    @Id
    @Column(name = "genre_id")
    private Integer genreId;


    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Film.class, fetch = FetchType.LAZY)
    private Film film;

    @Id
    @Column(name = "film_id")
    private Long filmId;

}
