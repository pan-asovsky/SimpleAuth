package dev.panasovsky.module.auth.util;

import dev.panasovsky.module.auth.jwt.Role;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class JwtUtils {

    public static JwtAuthentication generate(final Claims claims) {

        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setLogin(claims.getSubject());

        return jwtInfoToken;
    }

    private static Set<Role> getRoles(final Claims claims) {

        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

}