package ru.yandex.practicum.filmorate.model.foreignkey;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Data
public class UserLikeForeignKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "film_id")
    private Long filmId;
}
