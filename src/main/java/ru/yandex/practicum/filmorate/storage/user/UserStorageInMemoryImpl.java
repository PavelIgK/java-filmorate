package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class UserStorageInMemoryImpl implements UserStorage {
    private final Map<Integer, User> userMap = new HashMap<>();
    int id = 0;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User add(User user) {
        if (user.getId() != 0) {
            throw new ValidationException("В метод создания пришел пользователь с id.");
        }

        user = user.toBuilder().id(++id).friends(new HashSet<>()).build();
        userMap.put(user.getId(), user);
        log.debug("Добавлен новый пользователь. " + user);
        return user;
    }

    @Override
    public User update(User user) {
        if (userMap.containsKey(user.getId())) {
            user.setFriends(userMap.get(user.getId()).getFriends());
            userMap.put(user.getId(), user);
            log.debug("Данные пользователя изменены.");
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        log.debug("Запрос получения пользователя с id = {}.", id);
        if (!userMap.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден.");
        }
        return userMap.get(id);
    }
}
