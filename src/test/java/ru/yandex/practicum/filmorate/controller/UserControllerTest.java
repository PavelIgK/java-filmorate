package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void setup() {

        user = User.builder()
                .email("user@user.ru")
                .login("userLogin")
                .name("userName")
                .birthday(LocalDate.of(2000, 9, 30))
                .friends(new HashSet<>())
                .build();
    }

    @Test
    public void create_CorrectUser() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        checkCorrectRequest(sendPost(user), user);
        user = user.toBuilder().id(1).build();
        assertEquals(1, sendGet().size(), "Количество пользователей в списке некорректно.");
        assertEquals(user, sendGet().get(0), "Вернулся некорректный пользователь.");
    }

    @Test
    public void update_CorrectUser() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        sendPost(user);
        assertEquals(1, sendGet().size(), "Количество пользователей в списке некорректно.");
        user = user.toBuilder().id(1).name("UserNameNew").build();
        checkCorrectRequest(sendPut(user), user);
        assertEquals(1, sendGet().size(), "Количество пользователей в списке некорректно.");
        assertEquals(user.getName(), sendGet().get(0).getName());
    }

    @Test
    public void validateEmail() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        user.setEmail("");
        checkBadRequest(sendPost(user));
        user.setEmail("test?test@");
        checkBadRequest(sendPost(user));
        user.setEmail("@");
        checkBadRequest(sendPost(user));
        user.setEmail("test@");
        checkBadRequest(sendPost(user));
        user.setEmail("@test.ru");
        checkBadRequest(sendPost(user));
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");

    }

    @Test
    public void validateLogin() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        user.setLogin("");
        checkBadRequest(sendPost(user));
        user.setLogin(null);
        checkBadRequest(sendPost(user));
        user.setLogin("login login");
        checkBadRequest(sendPost(user));
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");

    }

    @Test
    public void validateName() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        user.setName("");
        User userForCheck = user.toBuilder().name(user.getLogin()).build();
        checkCorrectRequest(sendPost(user), userForCheck);
        assertEquals(1, sendGet().size(), "Количество пользователей в списке некорректно.");
        assertEquals(user.getLogin(), sendGet().get(0).getName(), "Имя не равно логину.");
    }

    @Test
    public void validateBirthday() throws Exception {
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        user.setBirthday(LocalDate.now().plusDays(1));
        checkBadRequest(sendPost(user));
        assertEquals(0, sendGet().size(), "Список пользователей должен быть пуст.");
        user.setBirthday(LocalDate.now());
        checkCorrectRequest(sendPost(user), user);
        assertEquals(1, sendGet().size(), "Количество пользователей в списке некорректно.");

    }



    private List<User> sendGet() throws Exception {
        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        return Arrays.asList(objectMapper.readValue(json, User[].class));
    }

    private ResultActions sendPost(User user) throws Exception {
        return mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
    }

    private ResultActions sendPut(User user) throws Exception {
        return mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
    }

    private void checkCorrectRequest(ResultActions resultActions, User user) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.name")).value(user.getName()))
                .andExpect(jsonPath(("$.login")).value(user.getLogin()))
                .andExpect(jsonPath(("$.birthday"))
                        .value(user.getBirthday().toString()))
                .andExpect(jsonPath(("$.email")).value(user.getEmail()));

    }

    private void checkBadRequest(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(status().isBadRequest());
    }
}
