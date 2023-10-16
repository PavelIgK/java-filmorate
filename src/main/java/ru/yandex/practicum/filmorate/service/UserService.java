package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User getUserById(int id) {
        return userStorage.getById(id);
    }

    public List<User> getCommonFriends(int id, int otherId) {
        log.debug("Запрос списка друзей общих между пользователем с id {} и пользователем с id {}", id, otherId);
        Set<Integer> userFriends = userStorage.getById(id).getFriends();
        Set<Integer> otherUserFriends = userStorage.getById(otherId).getFriends();

        Set<Integer> commonFriendsId = userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toSet());

        return userStorage.getAll()
                .stream()
                .filter(user -> commonFriendsId.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public void addFriend(int id, int friendId) {
        log.debug("Добавляем к пользователю с id {} друга с id {}", id, friendId);
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    public void removeFriend(int id, int friendId) {
        log.debug("Запрос удаления друга с id {} из друзей пользователем с id {}", friendId, id);
        User user = userStorage.getById(id);
        User friend = userStorage.getById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    public List<User> getFriends(int id) {
        log.debug("Запрос списка друзей пользователем с id {}", id);
        User user = userStorage.getById(id);
        List<User> friends = new ArrayList<>();
        for (int friendId : user.getFriends()) {
            friends.add(userStorage.getById(friendId));
        }
        return friends;
    }

}
