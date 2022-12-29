package dev.panasovsky.module.auth.repositories;

import dev.panasovsky.module.auth.model.Role;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRolename(final String rolename);

    @NonNull
    List<Role> findAll();

}