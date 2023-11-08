package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Структура пользователя.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  extends BaseEntity<Long> {

    @Id
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^\\S+$",
            message = "Логин содержит пробел."
    )

    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private Set<Friendship> friendship = new HashSet<>();
}
