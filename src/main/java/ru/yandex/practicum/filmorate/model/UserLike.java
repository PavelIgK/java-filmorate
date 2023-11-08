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
@Table(name = "like_user")
@IdClass(UserLikeForeignKey.class)
public class UserLike extends BaseEntity<Integer> {


    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Id
    @Column(name = "user_id")
    private Integer userId;


    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Film.class, fetch = FetchType.LAZY)
    private Film film;

    @Id
    @Column(name = "film_id")
    private Long filmId;

}
