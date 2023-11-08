package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Genre extends BaseEntity<Integer> implements Comparable<Genre> {
    String name;
    @Override
    public int compareTo(Genre o) {
        System.out.println("====" + this.getId().compareTo(o.getId()));
        return this.getId().compareTo(o.getId());
    }

}
