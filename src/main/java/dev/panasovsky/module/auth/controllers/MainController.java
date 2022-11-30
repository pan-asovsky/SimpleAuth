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


    // TODO: метод login() очень долгий - не менее 90мс. Разобраться и ускорить!
    // TODO: явное указание роли при регистрации доступно всем ролям!
    // TODO: выдавать токен при регистрации?
    // TODO: выдача JSON методами register(), login(), helloUser() и helloAdmin()
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
    @PostMapping("/api/admin/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

}