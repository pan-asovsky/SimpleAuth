package dev.panasovsky.module.auth.repositories;

import dev.panasovsky.module.auth.entities.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(final String login);

}