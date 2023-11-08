package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class FriendshipForeignKey implements Serializable {
    private Long userId;
    private Long friendId;
}
