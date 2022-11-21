package dev.panasovsky.module.auth.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Slf4j
@Component
public class JwtProvider {

//    private final SecretKey jwtAccessSecret;
//    private final SecretKey jwtRefreshSecret;
//
//    public JwtProvider(
//            @Value("${jwt.secret.access}") String jwtAccessSecret,
//            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
//    ) {
//        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
//        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
//    }


}