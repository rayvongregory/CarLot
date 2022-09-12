package com.genspark.CarLot.ServiceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.genspark.CarLot.DAO.UserDAO;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private Environment env;
    @Autowired
    private UserDAO userDAO;
//    private static final JedisPool pool = new JedisPool("localhost", 6379);
    private static final JedisPooled jedis = new JedisPooled("localhost", 6379);
    private final String JWT_SECRET = "secret.keys.jwt";

    @Override
    public ResponseEntity<HashMap<String, Object>> verifyToken(TokenRequest tokenRequest) {
        String token = tokenRequest.getAccessToken();
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty(JWT_SECRET)));
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
        DecodedJWT accessToken;
        try {
            accessToken = verifier.verify(token);
            String role = accessToken.getClaim("role").asString();
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
                {
                    put("msg", "Access token verified");
                    put("role", role);
                }
            });
        } catch (SignatureVerificationException e) {
            // the token received is not one I signed or is one that was altered by the user and is therefore invalid...
            // user should be forced to log out and maybe have their account locked our something...
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>(){
               {
                   put("err", e.getMessage());
               }
           });
        } catch (TokenExpiredException e) {
            // the access token received is expired... decode, get rtk, and find the rt is the jedis pool
            // if the rt isn't expired, simply generate a new access token with the same payload as the original access token
            // if the rt is expired, remove it from the pool and force the user to log out
            accessToken = JWT.decode(token);
            String id = accessToken.getClaim("id").asString();
            String role = accessToken.getClaim("role").asString();
            String rtk = accessToken.getClaim("rtk").asString();
            try {
                verifier.verify(jedis.get(rtk));
            } catch (TokenExpiredException ex) {
                jedis.del(rtk);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>() {
                    {
                        put("err", e.getMessage());
                    }
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>() {
                {
                    put("msg", "Here is your new access token");
                    put("role", role);
                    put("accessToken", generateNewAccessToken(id, role, rtk));
                }
            });
        }
    }

    @Override
    public void addRefreshToken(String rtk, Algorithm algorithm) {
        // use the rtk to retrieve the refresh token
        // store the access token to ensure we get back the same token from the front
        // when a user needs a new access token, simply use set() again to overwrite the previous
        jedis.set(rtk, JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 60 * 24 * 7))
                .sign(algorithm));
    }

    private String generateNewAccessToken(String id, String role, String rtk) {
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty(JWT_SECRET)));
        return JWT.create()
                .withClaim("id", id)
                .withClaim("role", role)
                .withClaim("rtk", rtk)
                .withIssuer("auth0")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 15))
                .sign(algorithm);
    }
}
