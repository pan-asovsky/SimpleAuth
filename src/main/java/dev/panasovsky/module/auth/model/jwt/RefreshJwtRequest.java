package dev.panasovsky.module.auth.model.jwt;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RefreshJwtRequest {

    private String login;
    private String refreshToken;

}