package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.model.jwt.JWTRequest;
import dev.panasovsky.module.auth.model.jwt.JWTResponse;
import dev.panasovsky.module.auth.components.JWTProvider;
import dev.panasovsky.module.auth.model.redis.RefreshJWT;
import dev.panasovsky.module.auth.exceptions.AuthException;
import dev.panasovsky.module.auth.model.jwt.JWTAuthentication;
import dev.panasovsky.module.auth.repositories.RefreshJWTRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final RefreshJWTRepository refreshJWTRepository;


    public JWTResponse login(@NonNull final JWTRequest authRequest) throws AuthException {

        final User user = userService.getByLogin(authRequest.getLogin());

        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(authRequest.getPassword(), user.getPassword())) {
            return getJWTResponse(user);
        } else {
            throw new AuthException("Wrong password!");
        }
    }

    public JWTResponse getAccessToken(@NonNull final String refreshToken) {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final User user = getUserFromJWTClaims(refreshToken);
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JWTResponse(accessToken, null);
        }

        return new JWTResponse(null, null);
    }

    public JWTResponse refresh(@NonNull final String refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final User user = getUserFromJWTClaims(refreshToken);
            return getJWTResponse(user);
        }

        return new JWTResponse(null, null);
    }

    private User getUserFromJWTClaims(@NonNull final String refreshToken) {

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String id = claims.getId();

        final Optional<RefreshJWT> userRefreshJWT = refreshJWTRepository.findById(claims.getId());
        final String savedRefreshToken = userRefreshJWT.map(RefreshJWT::getRefreshToken).orElse(null);

        if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
           return userService.getById(id);
        }

        throw new AuthException("Invalid JWT!");
    }

    private JWTResponse getJWTResponse(@NonNull final User user) {

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        final RefreshJWT refreshJWT = new RefreshJWT();
        refreshJWT.setId(user.getId().toString());
        refreshJWT.setRefreshToken(refreshToken);
        refreshJWTRepository.save(refreshJWT);

        return new JWTResponse(accessToken, refreshToken);
    }

    public JWTAuthentication getAuthInfo() {
        return (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}