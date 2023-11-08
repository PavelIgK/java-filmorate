package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "user_friend")
@IdClass(FriendshipForeignKey.class)
public class Friendship extends BaseEntity<Long> {


    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User userOne;

    @Id
    @Column(name = "user_id")
    private Long userId;


    @JoinColumn(name = "friend_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User userTwo;

    @Id
    @Column(name = "friend_id")
    private Long friendId;

    @EqualsAndHashCode.Exclude
    boolean confirmed;
}
