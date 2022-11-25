package dev.panasovsky.module.auth.jwt;

import dev.panasovsky.module.auth.model.Role;

import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.Collection;


@Getter
@Setter
@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private String login;
    private Set<Role> roles;
    private boolean authenticated;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return login; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return login; }

}
