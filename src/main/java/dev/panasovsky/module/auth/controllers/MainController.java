package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.model.User;
import dev.panasovsky.module.auth.services.AuthService;
import dev.panasovsky.module.auth.services.UserService;
import dev.panasovsky.module.auth.util.JwtAuthentication;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final AuthService authService;
    private final RoleRepository roleRepository;


    @GetMapping("/")
    public String getHelloMessage() {
        return "<h3>Hello!</h3>";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/user")
    public String getHelloMessageForUser() {
        return "<h3>Hello, user!</h3>";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getHelloMessageForAdmin() {
        return "<h3>Hello, admin!</h3>";
    }

    // ---
    // TODO: decode пароля в AuthService#login()
    // TODO: разобраться с классами Role
    // TODO: выдавать токен при регистрации?
    // TODO: разобраться с SecurityConfig#filterChain()
    // TODO: добавить поля регистрации (имя, почта и т.д)
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }

    // ---

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

    @PostMapping("/register")
    public String register(@RequestBody final User user) {
        return userService.register(user);
    }

}