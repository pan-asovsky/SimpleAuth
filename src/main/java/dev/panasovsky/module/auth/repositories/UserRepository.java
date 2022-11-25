package dev.panasovsky.module.auth.repositories;

import dev.panasovsky.module.auth.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(final String login);

    User findById(final UUID id);

}