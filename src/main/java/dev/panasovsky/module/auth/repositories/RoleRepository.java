package dev.panasovsky.module.auth.repositories;

import dev.panasovsky.module.auth.entities.Role;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}