package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.user.Friendship;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


@Primary
@Component
@RequiredArgsConstructor
public class UserStorageDBImpl implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private String query = "";


    @Override
    public List<User> findAll() {
        query = "SELECT user_id, email, login, name, birthday FROM users";
        return jdbcTemplate.query(query, this::mapUser);
    }

    @Transactional
    @Override
    public User add(User user) {

        query = "INSERT INTO users (user_id, email, login, name, birthday) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(query,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        return findById(user.getId()).get();
    }

    @Transactional
    @Override
    public User update(User user) {
        query = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?";

        jdbcTemplate.update(query,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());


        //Почистим если есть неактуальные пользователи в друзьях
        Set<Friendship> newFriends = user.getFriendship();
        Set<Friendship> oldFriends = findFriendshipByUserId(user.getId());

        Set<Friendship> removeFriends = new HashSet<>(oldFriends);
        removeFriends.removeAll(newFriends);
        removeFriends.forEach(it -> removeFriendship(user.getId(), it.getFriendId()));

        Set<Friendship> toAddFriendships = new HashSet<>(newFriends);
        toAddFriendships.removeAll(oldFriends);
        toAddFriendships.forEach(it -> addFriendship(user.getId(), it.getFriendId(), it.isConfirmed()));

        return findById(user.getId()).get();
    }

    @Override
    public Optional<User> findById(Long id) {
        query = "SELECT user_id, email, login, name, birthday FROM users WHERE user_id = ?";
        return jdbcTemplate.query(query, this::mapUser, id).stream().findAny();
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        Long userId = rs.getLong("user_id");
        String name = rs.getString("name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        User user = User.builder()
                .id(userId)
                .name(name)
                .login(login)
                .email(email)
                .birthday(birthday)
                .build();

        user.setFriendship(findFriendshipByUserId(userId));
        return user;
    }

    private Friendship mapFriendship(ResultSet rs, int rowNum) throws SQLException {
        long friendId = rs.getLong("friend_id");
        boolean confirmed = rs.getBoolean("confirmed");
        return Friendship.builder()
                .friendId(friendId)
                .confirmed(confirmed)
                .build();
    }

    private void addFriendship(Long userId, Long friendId, boolean isConfirmed) {
        query = "INSERT INTO user_friend (user_id, friend_id, confirmed) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, userId, friendId, isConfirmed);
    }

    private void removeFriendship(Long userId, Long friendId) {
        query = "DELETE FROM user_friend WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(query, userId, friendId);
    }

    private Set<Friendship> findFriendshipByUserId(Long userId) {
        query = "SELECT friend_id, confirmed FROM user_friend WHERE user_id = ?";
        return new HashSet<>(jdbcTemplate.query(query, this::mapFriendship, userId));
    }

}
