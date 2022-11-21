package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.entities.Role;
import dev.panasovsky.module.auth.entities.User;
import dev.panasovsky.module.auth.services.UserService;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

    @PostMapping("/register")
    public String addUser(@RequestBody final User user) {
        return userService.registerUser(user);
    }

}