package ru.yandex.practicum.filmorate.storage.db;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class GenreStorageDBImpl implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String query = "SELECT genre_id, name FROM genre";
        return jdbcTemplate.query(query, this::mapGenre);
    }

    @Override
    public Genre add(Genre genre) {
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        String query = "SELECT genre_id, name FROM genre WHERE genre_id = ?";
        return jdbcTemplate.query(query, this::mapGenre, id).stream().findAny();
    }

    private Genre mapGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Integer id = resultSet.getInt("genre_id");
        String name = resultSet.getString("name");

        return Genre.builder()
                .id(id)
                .name(name)
                .build();
    }
}