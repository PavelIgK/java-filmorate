package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private Long id = 0L;

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User add(User user) {
        if (Optional.ofNullable(user.getId()).isPresent()) {
            throw new ValidationException("В метод создания пришел пользователь с id");
        }
        user = user.toBuilder().id(++id).build();
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User getUserById(Long id) {
        return userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + id));
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        log.debug("Запрос списка друзей общих между пользователем с id {} и пользователем с id {}", id, otherId);
        Set<Long> userFriends = userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + id))
                .getFriendship()
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toSet());
        Set<Long> otherUserFriends = userStorage.getById(otherId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + otherId))
                .getFriendship()
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toSet());
        ;

        Set<Long> commonFriendsId = userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toSet());

        return userStorage.getAll()
                .stream()
                .filter(user -> commonFriendsId.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public void addFriend(Long id, Long friendId) {
        log.debug("Добавляем к пользователю с id {} друга с id {}", id, friendId);


        User user = userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + id));
        User friend = userStorage.getById(friendId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + friendId));


        user.getFriendship().add(Friendship.builder()
                .friendId(friendId)
                .build());
        userStorage.update(user);
    }

    public void removeFriend(Long id, Long friendId) {
        log.debug("Запрос удаления друга с id {} из друзей пользователем с id {}", friendId, id);
        User user = userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + id));
        userStorage.getById(friendId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + friendId));

        user.getFriendship().remove(Friendship.builder()
                .friendId(friendId).build());
        userStorage.update(user);
    }

    public List<User> getFriends(Long id) {
        log.debug("Запрос списка друзей пользователем с id {}", id);
        User user = userStorage.getById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с id = " + id));

        return user.getFriendship()
                .stream()
                .sorted(Comparator.comparing(Friendship::getFriendId))
                .map(Friendship::getFriendId)
                .map(userStorage::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
