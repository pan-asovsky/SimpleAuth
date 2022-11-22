package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.jwt.JWTRequest;
import dev.panasovsky.module.auth.jwt.JwtProvider;
import dev.panasovsky.module.auth.jwt.JwtResponse;
import dev.panasovsky.module.auth.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();


//    public JwtResponse login(@NonNull final JWTRequest authRequest) {
//
//        final User user = userService.getByLogin(authRequest.getLogin());
//
//    }


}
