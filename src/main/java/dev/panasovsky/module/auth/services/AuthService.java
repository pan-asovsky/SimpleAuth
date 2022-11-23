package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.jwt.JWTRequest;
import dev.panasovsky.module.auth.jwt.JwtResponse;
import dev.panasovsky.module.auth.components.JwtProvider;
import dev.panasovsky.module.auth.jwt.JwtAuthentication;
import dev.panasovsky.module.auth.exceptions.AuthException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();


    public JwtResponse login(@NonNull final JWTRequest authRequest) throws AuthException {

        final User user = userService.getByLogin(authRequest.getLogin());

        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password!");
        }
    }

    public JwtResponse getAccessToken(@NonNull final String refreshToken) {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull final String refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.getByLogin(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(login, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT!");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
