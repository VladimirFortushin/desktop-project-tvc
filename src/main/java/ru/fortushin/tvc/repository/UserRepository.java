package ru.fortushin.tvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.tvc.model.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
}
