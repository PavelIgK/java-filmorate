package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.validate.UserValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserValidation userValidation = new UserValidation();

    protected final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @GetMapping()
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        userValidation.isValid(user);
        if (user.getId() != 0) {
            throw new ValidationException("В метод создания пришел пользователь с id");
        }
        user = user.toBuilder().id(++id).build();
        users.put(user.getId(), user);
        log.debug("Добавлен новый пользователь. " + user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        userValidation.isValid(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Обновлен пользователь." + user);
        } else {
            throw new ValidationException("Пользователя с такими id нет");
        }
        return user;
    }
}
