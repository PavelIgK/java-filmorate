package ru.yandex.practicum.filmorate.storage.db;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class FilmStorageDBImpl implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenreStorageDBImpl genreStorageDB;

    @Transactional
    @Override
    public Film add(Film film) {
        String query = "INSERT INTO film (film_id, name, description, mpa_id, release_date, duration) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration());

        addGenre(film, film.getGenres());
        return findById(film.getId()).get();
    }

    @Transactional
    @Override
    public Film update(Film film) {
        String query = "UPDATE film SET name = ?, description = ?, mpa_id = ?, release_date = ?, duration = ?" +
                " WHERE film_id = ?";

        jdbcTemplate.update(query,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());

        //лайки
        Set<Long> newUsersId = film.getLikes();
        List<Long> oldUsersId = findLikesByFilmId(film.getId());
        List<Long> addUsers = new ArrayList<>(newUsersId);
        addUsers.remove(oldUsersId);
        addLikes(film, addUsers);

        List<Long> removeLikes = new ArrayList<>(oldUsersId);
        oldUsersId.remove(newUsersId);
        removeLikes(film, removeLikes);

        //жанры
        Set<Integer> newGenreIds = film.getGenres()
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        List<Integer> currentGenresId = findGenresIdByFilmId(film.getId());

        List<Integer> newGenreId = new ArrayList<>(newGenreIds);
        newGenreId.removeAll(currentGenresId);
        addGenreList(film, newGenreId);

        List<Integer> removeGenres = new ArrayList<>(currentGenresId);
        removeGenres.removeAll(newGenreIds);
        removeGenreList(film, removeGenres);

        return findById(film.getId()).get();
    }

    @Override
    public Optional<Film> findById(Long id) {
        String query = "SELECT f.film_id" +
                ", f.name as film_name" +
                ", f.description as film_description" +
                ", f.mpa_id" +
                ", f.release_date" +
                ", f.duration" +
                ", m.name as mpa_name" +
                ", m.description as mpa_description" +
                " FROM film AS f LEFT JOIN mpa as m ON f.mpa_id = m.mpa_id" +
                " WHERE f.film_id = ?";
        return jdbcTemplate.query(query, this::mapFilm, id).stream().findAny();
    }

    @Override
    public List<Film> findAll() {
        String query = "SELECT f.film_id" +
                ", f.name as film_name" +
                ", f.description as film_description" +
                ", f.mpa_id" +
                ", f.release_date" +
                ", f.duration" +
                ", m.name as mpa_name" +
                ", m.description as mpa_description" +
                " FROM film AS f LEFT JOIN mpa as m ON f.mpa_id = m.mpa_id";
        return jdbcTemplate.query(query, this::mapFilm);
    }

    private Film mapFilm(ResultSet rs, int rowNum) throws SQLException {
        Long filmId = rs.getLong("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("film_description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        Integer mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa_name");
        String mpaDescription = rs.getString("mpa_description");


        Mpa mpa = Mpa.builder()
                .id(mpaId)
                .name(mpaName)
                .description(mpaDescription)
                .build();

        Film film = Film.builder()
                .id(filmId)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(mpa)
                .build();

        film.setGenres(findGenresByFilmId(film.getId()));
        film.setLikes(new HashSet<>(findLikesByFilmId(film.getId())));
        return film;
    }

    private List<Long> findLikesByFilmId(Long filmId) {
        String query = "SELECT user_id FROM film_like WHERE film_id = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }

    private void addLikes(Film film, List<Long> userIds) {
        String query = "INSERT INTO film_like (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(query,
                userIds,
                50,
                (PreparedStatement ps, Long userId) -> {
                    ps.setLong(1, film.getId());
                    ps.setLong(2, userId);
                });
    }

    private void removeLikes(Film film, List<Long> userIds) {
        String query = "DELETE FROM film_like WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.batchUpdate(query,
                userIds,
                50,
                (PreparedStatement ps, Long userId) -> {
                    ps.setLong(1, film.getId());
                    ps.setLong(2, userId);
                });
    }

    private void addGenre(Film film, Set<Genre> genres) {
        String query = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(query,
                genres,
                50,
                (PreparedStatement ps, Genre genre) -> {
                    ps.setLong(1, film.getId());
                    ps.setInt(2, genre.getId());
                });
    }

    private void addGenreList(Film film, List<Integer> genreIds) {
        String query = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(query,
                genreIds,
                50,
                (PreparedStatement ps, Integer genreId) -> {
                    ps.setLong(1, film.getId());
                    ps.setInt(2, genreId);
                });
    }

    private void removeGenreList(Film film, List<Integer> genreIds) {
        String query = "DELETE FROM film_genre WHERE film_id = ? AND genre_id = ?";
        jdbcTemplate.batchUpdate(query,
                genreIds,
                50,
                (PreparedStatement ps, Integer genreId) -> {
                    ps.setLong(1, film.getId());
                    ps.setInt(2, genreId);
                });
    }

    private List<Integer> findGenresIdByFilmId(Long filmId) {
        String query = "SELECT genre_id FROM film_genre WHERE film_id = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getInt("genre_id"), filmId);
    }

    private Set<Genre> findGenresByFilmId(Long filmId) {
        return findGenresIdByFilmId(filmId).stream()
                .map(genreStorageDB::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
