package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.services.AuthService;
import dev.panasovsky.module.auth.model.jwt.JWTAuthentication;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final AuthService authService;
    private final RoleRepository roleRepository;


    // TODO: выдавать токен при регистрации?
    // TODO: разобраться с SecurityConfig#filterChain()


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/api/hello/user")
    public ResponseEntity<String> helloUser() {

        final JWTAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getLogin() + "!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/hello/admin")
    public ResponseEntity<String> helloAdmin() {

        final JWTAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getLogin() + "!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/auth/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

}