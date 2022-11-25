package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.jwt.JWTRequest;
import dev.panasovsky.module.auth.jwt.JwtResponse;
import dev.panasovsky.module.auth.services.AuthService;
import dev.panasovsky.module.auth.jwt.RefreshJwtRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody final JWTRequest authRequest) {

        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(
            @RequestBody final RefreshJwtRequest request) {

        final JwtResponse accessToken = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(accessToken);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(
            @RequestBody final RefreshJwtRequest request) {

        final JwtResponse newRefreshToken = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(newRefreshToken);
    }

}