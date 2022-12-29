package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.util.JsonConvertHelper;
import dev.panasovsky.module.auth.model.UserDetailsPrincipal;
import dev.panasovsky.module.auth.repositories.UserRepository;
import dev.panasovsky.module.auth.exceptions.RegistrationException;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final static int USER_ROLE_ID = 2;
    private final static String ROLE_NAME = "USER";


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

    public JsonNode register(final User user) {

        final String error = "error";
        final String success = "success";
        final String registered = "registered";

        try {
            registerUser(user);
        } catch (final RegistrationException e) {
            log.debug("attempt to register with an existing login: {}", user.getLogin());
            return JsonConvertHelper.getJsonResponse(error, e.getMessage());
        }

        return JsonConvertHelper.getJsonResponse(success, registered);
    }

    private void registerUser(final User user) throws RegistrationException {

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (user.getUser_role() == null) setUserRole(user);

        if (getByLogin(user.getLogin()) == null) userRepository.save(user);
        else throw new RegistrationException("this login busy");
    }

    private void setUserRole(final User user) {

        final Role userRole = new Role();
        userRole.setId(USER_ROLE_ID);
        userRole.setRolename(ROLE_NAME);
        user.setUser_role(userRole);
    }

}