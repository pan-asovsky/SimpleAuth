package dev.panasovsky.module.auth.model.jwt;

import lombok.Getter;
import lombok.AllArgsConstructor;


@Getter
@AllArgsConstructor
public class JWTResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}