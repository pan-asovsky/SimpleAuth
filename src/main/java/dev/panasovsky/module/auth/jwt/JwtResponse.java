package dev.panasovsky.module.auth.jwt;

import lombok.Getter;
import lombok.AllArgsConstructor;


@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}