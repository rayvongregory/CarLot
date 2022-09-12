package com.genspark.CarLot.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Objects;

public class TokenTest {

    // Tests for learning more about the "verify" function and the exceptions it throws

    @Test
    public void expiredTokenTest() {
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull("secret"));
        String rtk = RandomStringUtils.randomAlphabetic(20); // refresh token key
        String token = JWT.create()
                .withClaim("id", 1)
                .withClaim("role", "user")
                .withClaim("rtk", rtk)
                .withIssuer("auth0")
                .withIssuedAt(Instant.now().minusSeconds(2))
                .withExpiresAt(Instant.now().minusSeconds(1)) // <-- JWT needs to be expired for a full second for verifier to throw TokenExpiredException
                .sign(algorithm);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
        try {
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            System.out.println("Expired tokens are not valid");
            System.out.println(e);
            assertFalse(false);
        }
    }

    @Test
    public void differentSignaturesTest() {
        Algorithm algorithm1 = Algorithm.HMAC256(Objects.requireNonNull("thisSecret"));
        Algorithm algorithm2 = Algorithm.HMAC256(Objects.requireNonNull("thatSecret"));
        String rtk = RandomStringUtils.randomAlphabetic(20);  // refresh token key
        String token = JWT.create()
                .withClaim("id", 1)
                .withClaim("role", "user")
                .withClaim("rtk", rtk)
                .withIssuer("auth0")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(15 * 60))
                .sign(algorithm1);
        System.out.println(token);
        JWTVerifier verifier = JWT.require(algorithm2).withIssuer("auth0").build();
        try {
            verifier.verify(token);
        } catch (SignatureVerificationException e) {
            assertFalse(false);
            System.out.println("Token is not valid because signatures are different");
            System.out.println(e);
        }
    }

    @Test
    public void swappedPayloadsTest() {
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull("secret"));
        String token1 = JWT.create()
                .withClaim("id", 1)
                .withClaim("role", "user")
                .withClaim("rtk", "vYSQuotMyptEdcnkqNnY") // refresh token key
                .withIssuer("auth0")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(15 * 60))
                .sign(algorithm);
        String token2 = JWT.create()
                .withClaim("color", "all the colors")
                .withClaim("age", "old")
                .withClaim("height", "tall")
                .withIssuer("auth0")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(15 * 60))
                .sign(algorithm);
        System.out.println(token1);
        System.out.println(token2);
        String[] token1Parts = token1.split("\\.");
        String[] token2Parts = token2.split("\\.");
        String token1WithToken2Payload = token1Parts[0]+"."+token2Parts[1]+"."+token1Parts[2];
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
        try {
            DecodedJWT jwt = verifier.verify(token1WithToken2Payload);
            jwt.getClaim("butt");
        } catch (JWTVerificationException e) {
            System.out.println("Changing the payload causes an error");
            System.out.println(e);
            assertFalse(false);
        }
    }
}
