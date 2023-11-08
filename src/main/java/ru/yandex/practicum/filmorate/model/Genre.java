package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class Genre  extends BaseEntity<Integer> implements Comparable<Genre> {

    @Id
    @Column(name = "genre_id")
    private Integer id;

    String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="film_genre",
            joinColumns=  @JoinColumn(name="genre_id", referencedColumnName="genre_id"),
            inverseJoinColumns= @JoinColumn(name="film_id", referencedColumnName="film_id") )
    private Set<Film> films = new HashSet<>();

    @Override
    public int compareTo(Genre o) {
        return this.getId().compareTo(o.getId());
    }

}
