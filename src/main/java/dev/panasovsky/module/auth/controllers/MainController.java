package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.entities.Role;
import dev.panasovsky.module.auth.entities.User;
import dev.panasovsky.module.auth.services.UserService;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/")
    public String getHelloMessage() {
        return "<h3>Hello!</h3>";
    }

    @PreAuthorize("hasAnyRole('ROLE_user', 'ROLE_admin')")
    @GetMapping("/user")
    public String getHelloMessageForUser() {
        return "<h3>Hello, user!</h3>";
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/admin")
    public String getHelloMessageForAdmin() {
        return "<h3>Hello, admin!</h3>";
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping("/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

    @PostMapping("/register")
    public User addUser(@RequestBody final User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.addUser(user);
    }

}