package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.entities.User;
import dev.panasovsky.module.auth.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        final User user = userRepository.findByLogin(login);
        return new UserDetailsPrincipal(user);
    }

    public String register(final User user) {

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        final User addUser = addUser(user);
        return "Successfully registered " + addUser.getLogin();
    }

    private User addUser(final User user) {
        return userRepository.save(user);
    }

}