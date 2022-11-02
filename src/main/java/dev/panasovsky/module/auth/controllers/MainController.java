package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.entities.Role;
import dev.panasovsky.module.auth.entities.User;
import dev.panasovsky.module.auth.services.UserService;
import dev.panasovsky.module.auth.repositories.RoleRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class MainController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getHelloMessage() {
        return "<h3>Hello!</h3>";
    }

    @GetMapping("/user")
    public String getHelloMessageForUser() {
        return "<h3>Hello, user!</h3>";
    }

    @GetMapping("/admin")
    public String getHelloMessageForAdmin() {
        return "<h3>Hello, admin!</h3>";
    }

    @PostMapping("/addrole")
    public Role addRole(@RequestBody final Role role) {
        return roleRepository.save(role);
    }

    @PostMapping("/adduser")
    public User addUser(@RequestBody final User user) {
        return userService.addUser(user);
    }

}