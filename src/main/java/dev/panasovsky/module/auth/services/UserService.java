package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.model.UserDetailsPrincipal;
import dev.panasovsky.module.auth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        final User user = userRepository.findByLogin(login);
        return new UserDetailsPrincipal(user);
    }

    public User getByLogin(final String login) {
        return userRepository.findByLogin(login);
    }

    public User getById(final String id) {
        return userRepository.findById(UUID.fromString(id));

    }

    public String register(final User user) {

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (user.getUser_role() == null) setRoleUser(user);

        final User addUser = addUser(user);
        return "Successfully registered " + addUser.getLogin();
    }

    private void setRoleUser(final User user) {

        final Role userRole = new Role();
        userRole.setId(2);
        userRole.setRolename("USER");
        user.setUser_role(userRole);
    }

    private User addUser(final User user) {
        return userRepository.save(user);
    }

}