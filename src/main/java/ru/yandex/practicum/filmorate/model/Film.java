package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.util.validate.ReleaseDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Структура фильма.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "film")
public class Film extends BaseEntity<Long> {

    @Id
    @Column(name = "film_id")
    private Long id;

    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания - 200 символов.")
    private String description;

    @ReleaseDate(message = "Дата релиза слишком старая.")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private long duration;

    private int rate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_genre",
            joinColumns=  @JoinColumn(name="film_id", referencedColumnName="film_id"),
            inverseJoinColumns= @JoinColumn(name="genre_id", referencedColumnName="genre_id") )
    private Set<Genre> genres = new HashSet<>();

    @NotNull(message = "Рейтинг MPA не может быть пустым")
    @OneToOne
    Mpa mpa;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_film_like",
            joinColumns=  @JoinColumn(name="film_id", referencedColumnName="film_id"),
            inverseJoinColumns= @JoinColumn(name="user_id", referencedColumnName="user_id") )
    private Set<User> likes = new HashSet<>();


}
