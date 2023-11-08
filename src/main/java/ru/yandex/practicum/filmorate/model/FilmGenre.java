//package ru.yandex.practicum.filmorate.model;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//import javax.persistence.*;
//
//@Data
//@SuperBuilder(toBuilder = true)
//@NoArgsConstructor
//@Entity
//@Table(name = "film_genre")
//@IdClass(FilmGenreForeignKey.class)
//public class FilmGenre {
//
//    @ManyToOne
//    @MapsId("id")
//    @JoinColumn(name = "genre_id")
//    private Genre genre;
//
//    @Id
//    @Column(name = "genre_id")
//    private Long id;
//
//    @ManyToOne
//    @MapsId("filmId")
//    @JoinColumn(name = "film_id")
//    private Film film;
//
//    @Id
//    @Column(name = "film_id")
//    private Long filmId;
//
//}
