package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class MpaStorageDBImpl implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Mpa> findAll() {
        String query = "SELECT mpa_id, name, description FROM mpa";
        return jdbcTemplate.query(query, this::mapMpa);
    }

    @Override
    public Mpa add(Mpa mpa) {
        return null;
    }

    @Override
    public Mpa update(Mpa mpa) {
        return null;
    }

    @Override
    public Optional<Mpa> findById(Integer id) {
        String query = "SELECT mpa_id, name, description FROM mpa WHERE mpa_id = ?";
        return jdbcTemplate.query(query, this::mapMpa, id).stream().findAny();
    }

    private Mpa mapMpa(ResultSet resultSet, int rowNum) throws SQLException {
        Integer id = resultSet.getInt("mpa_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");

        return Mpa.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
