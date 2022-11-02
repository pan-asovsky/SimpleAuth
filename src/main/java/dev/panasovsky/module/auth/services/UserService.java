package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.entities.User;
import dev.panasovsky.module.auth.repositories.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        final User user = userRepository.findByLogin(login);
        return new UserDetailsPrincipal(user);
    }

    public User addUser(final User user) {
        return userRepository.save(user);
    }

}