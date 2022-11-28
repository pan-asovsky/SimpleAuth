package dev.panasovsky.module.auth.components;

import dev.panasovsky.module.auth.model.jwt.JwtAuthentication;
import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.HashSet;


@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final RoleRepository roleRepository;


    public JwtAuthentication generate(final Claims claims) {

        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setLogin(claims.getSubject());

        return jwtInfoToken;
    }

    private Set<Role> getRoles(final Claims claims) {

        final String rolename = claims.get("role", String.class);

        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRolename(rolename));
        return roles;
    }

}