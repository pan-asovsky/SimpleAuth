package dev.panasovsky.module.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

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

}