package ru.yandex.practicum.filmorate.model.film;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.model.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Genre extends BaseEntity<Integer> implements Comparable<Genre> {

    String name;

    @Override
    public int compareTo(Genre o) {
        return this.getId().compareTo(o.getId());
    }

}
