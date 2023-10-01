package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;
    private User user;

    @BeforeEach
    void init() {
        userController = new UserController();

        user = User.builder()
                .email("user@user.ru")
                .login("userLogin")
                .name("userName")
                .birthday(LocalDate.of(2000, 9, 30))
                .build();
    }

    @Test
    public void create_CorrectUser() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
        userController.addUser(user);
        assertEquals(2, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void update_CorrectUser() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
        user = userController.getUsers().get(0);
        user.setName("NewName");
        userController.updateUser(user);
        assertEquals(user, userController.getUsers().get(0));
    }

    @Test
    public void create_UserWithId() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
        assertThrows(ValidationException.class,
                () -> userController.addUser(userController.getUsers().get(0)), "ожидался ValidationException");
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void create_UserWithBlankEmail() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setEmail("");
        assertThrows(ValidationException.class,
                () -> userController.addUser(user), "ожидался ValidationException");
        assertEquals(0, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void create_UserWithBlankLogin() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setLogin("");
        assertThrows(ValidationException.class,
                () -> userController.addUser(user), "ожидался ValidationException");
        assertEquals(0, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void create_UserLoginWithSpace() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setLogin("login login");
        assertThrows(ValidationException.class,
                () -> userController.addUser(user), "ожидался ValidationException");
        assertEquals(0, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void create_UserWithBlankName() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setName("");
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
        assertEquals(user.getLogin(), userController.getUsers().get(0).getName());
    }

    @Test
    public void create_UserWithFutureBirthday() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class,
                () -> userController.addUser(user), "ожидался ValidationException");
        assertEquals(0, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }

    @Test
    public void create_UserWithBirthdayToday() {
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пуст.");
        user.setBirthday(LocalDate.now());
        userController.addUser(user);
        assertEquals(1, userController.getUsers().size(), "Количество пользователей в списке некорректно.");
    }
}
