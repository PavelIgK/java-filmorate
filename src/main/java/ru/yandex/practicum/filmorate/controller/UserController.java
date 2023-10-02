package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.List;

/**
 * Пользователи.
 * Обработчик запросов по эндпоинту /users.
 */
@Slf4j
@RestController
@Component
@RequestMapping("/users")
public class UserController extends Controller<User> {

    /**
     * Получаем всех пользователей.
     * @return Список пользователей.
     */
    @Override
    @GetMapping()
    public List<User> get() {
        return super.get();
    }

    /**
     * Добавляем пользователя если он валиден.
     * @param user входящая сущность.
     * @return Добавленный пользователь.
     */
    @Override
    @PostMapping()
    public User add(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user =  user.toBuilder().name(user.getLogin()).build();
        }
        return super.add(user);
    }

    /**
     * Обновляем пользователя если он валиден.
     * @param user сущность.
     * @return обновленный пользователь
     */
    @Override
    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        return super.update(user);
    }

    /**
     * Валидируем пользователя.
     * @param user сущность.
     */
    @Override
    protected void validate(User user) {
        log.debug("Validate in film");
    }
}
