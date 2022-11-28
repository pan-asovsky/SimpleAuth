package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.model.jwt.JWTRequest;
import dev.panasovsky.module.auth.model.jwt.JwtResponse;
import dev.panasovsky.module.auth.model.jwt.JwtAuthentication;
import dev.panasovsky.module.auth.components.JwtProvider;
import dev.panasovsky.module.auth.exceptions.AuthException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getId().toString(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password!");
        }
    }

    public JwtResponse getAccessToken(@NonNull final String refreshToken) {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String id = claims.getId();
            final String savedRefreshToken = refreshStorage.get(id);

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.getById(id);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull final String refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String id = claims.getId();
            final String savedRefreshToken = refreshStorage.get(id);

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.getById(id);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(id, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT!");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}