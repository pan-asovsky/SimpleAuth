package dev.panasovsky.module.auth.components;

import dev.panasovsky.module.auth.model.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;

import java.util.Date;
import java.time.ZoneId;
import java.time.Instant;
import java.security.Key;
import java.time.LocalDateTime;


@Slf4j
@Component
public class JWTProvider {

    @Value("${jwt.access.public}")
    private RSAPublicKey jwtAccessPublic;
    @Value("${jwt.access.private}")
    private RSAPrivateKey jwtAccessPrivate;
    @Value("${jwt.refresh.public}")
    private RSAPublicKey jwtRefreshPublic;
    @Value("${jwt.refresh.private}")
    private RSAPrivateKey jwtRefreshPrivate;


    public String generateAccessToken(@NonNull final User user) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setId(user.getId().toString())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessPrivate)
                .claim("role", user.getUser_role().getRolename())
                .compact();
    }

    public String generateRefreshToken(@NonNull final User user) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(10)
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getLogin())
                .setId(user.getId().toString())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshPrivate)
                .compact();
    }

    public boolean validateAccessToken(@NonNull final String accessToken) {
        return validateToken(accessToken, jwtAccessPublic);
    }

    public boolean validateRefreshToken(@NonNull final String refreshToken) {
        return validateToken(refreshToken, jwtRefreshPublic);
    }

    private boolean validateToken(@NonNull final String token,
                                  @NonNull final Key secret) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (final ExpiredJwtException expEx) {
            log.error("Token expired: ", expEx);
        } catch (final UnsupportedJwtException unsEx) {
            log.error("Unsupported JWT: ", unsEx);
        } catch (final MalformedJwtException mExc) {
            log.error("Malformed JWT: ", mExc);
        } catch (final SignatureException sExc) {
            log.error("Invalid signature: ", sExc);
        } catch (final Exception e) {
            log.error("Invalid token: ", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull final String token) {
        return getClaims(token, jwtAccessPublic);
    }

    public Claims getRefreshClaims(@NonNull final String token) {
        return getClaims(token, jwtRefreshPublic);
    }

    private Claims getClaims(@NonNull final String token, @NonNull final Key secret) {

        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}