package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

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

    @Override
    public int compareTo(Genre o) {
        return this.getId().compareTo(o.getId());
    }

}
