package dev.panasovsky.module.auth;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JWTRequest {

    private String login;
    private String password;

}