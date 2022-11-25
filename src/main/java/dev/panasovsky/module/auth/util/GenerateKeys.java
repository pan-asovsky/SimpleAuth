package dev.panasovsky.module.auth.util;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.data.util.Pair;


public class GenerateKeys {

    public static void main(final String[] args) {

        final Pair<String, String> keyPair = generateRS512();
        System.out.println(keyPair.getFirst());
        System.out.println(keyPair.getSecond());
    }

    @Deprecated
    private static String generateRS512Keys() {
        return Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.RS512).getEncoded());
    }
    
    private static Pair<String, String> generateRS512() {

        final byte[] encodedPublic = Keys.keyPairFor(SignatureAlgorithm.RS512).getPublic().getEncoded();
        final String publicKey = Encoders.BASE64.encode(encodedPublic);

        final byte[] encodedPrivate = Keys.keyPairFor(SignatureAlgorithm.RS512).getPrivate().getEncoded();
        final String privateKey = Encoders.BASE64.encode(encodedPrivate);

        return Pair.of(publicKey, privateKey);
    }

}