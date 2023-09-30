package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    protected final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @GetMapping()
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        user.setId(++id);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь. " + user);
        return user;
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {

        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Обновлен пользователь." + user);
        } else {
            throw new ValidationException("Пользователя с такими id нет");
        }

        return user;
    }


}
