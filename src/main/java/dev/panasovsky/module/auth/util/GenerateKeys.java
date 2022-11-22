package dev.panasovsky.module.auth.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;


public class GenerateKeys {

    public static void main(final String[] args) {

        System.out.println(generateRS256Keys());
        System.out.println(generateRS256Keys());
    }

    private static String generateRS256Keys() {
        return Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
    }

}