package ru.fortushin.tvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.fortushin.tvc.validator.ValidEmail;

@Entity
@Table(name = "users_authorities")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "login")
    private String login;
    @NotEmpty(message = "The name field shouldn't be empty")
    @Size(min = 2, max = 100, message = "User name should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private String role;
    @NotEmpty(message = "The position field shouldn't be empty")
    @Size(min = 2, max = 100, message = "Position name should be between 2 and 100 characters")
    @Column(name = "position")
    private String jobTitle;
    @Column(name = "password")
    private String password;
    @Column(name = "email")

    @NotEmpty
    private String email;

    public User() {
    }

    public User(String login, String name, String role, String jobTitle, String password, String email) {
        this.login = login;
        this.name = name;
        this.role = role;
        this.jobTitle = jobTitle;
        this.password = password;
        this.email = email;
    }

}
