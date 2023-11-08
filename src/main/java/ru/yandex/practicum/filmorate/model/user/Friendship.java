package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.model.foreignkey.FriendshipForeignKey;

import javax.persistence.*;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "user_friend")
@IdClass(FriendshipForeignKey.class)
public class Friendship {

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
