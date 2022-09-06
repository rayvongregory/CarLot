package com.genspark.CarLot.ServiceImpl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.genspark.CarLot.DAO.UserDAO;
import com.genspark.CarLot.Entity.User;
import com.genspark.CarLot.Request.AuthCookieRequest;
import com.genspark.CarLot.Request.LoginUserRequest;
import com.genspark.CarLot.Request.RegisterUserRequest;
import com.genspark.CarLot.Service.UserService;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Environment env;
    private final String emailRegex = "^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$";
    private final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    private final String JWT_SECRET = "secret.keys.jwt";

    public UserServiceImpl() {}

    @Override
    public ResponseEntity<HashMap<String, Object>> registerUser(RegisterUserRequest registerUserRequest) {
        String email = registerUserRequest.getEmail();
        String confirmEmail = registerUserRequest.getConfirmEmail();
        if(!email.matches(emailRegex)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Please enter a valid email address.");
        }});
        if(!email.equals(confirmEmail)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Emails do not match.");
        }});
        String password = registerUserRequest.getPassword();
        String confirmPassword = registerUserRequest.getConfirmPassword();
        if(!password.matches(passwordRegex)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Please enter a password that is at least 8 characters long, and contains at least one letter, at least one number, and at least one special character.");
        }});
        if(!password.equals(confirmPassword)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Passwords do not match.");
        }});
        try {
            String fname =registerUserRequest.getFname();
            String lname = registerUserRequest.getLname();
            User user = new User();
            user.setFname(fname);
            user.setLname(lname);
            user.setEmail(email);
            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            user.setPassword(passwordEncryptor.encryptPassword(password));
            userDAO.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){
                {
                    put("err", "An account with this email address already exists.");
                }
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("msg", "New user successfully registered.");
            }
        });
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> loginUser(LoginUserRequest loginUserRequest) {
        String email = loginUserRequest.getEmail();
        if(!email.matches(emailRegex)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Please enter a valid email address.");
        }});
        String password = loginUserRequest.getPassword();
        if(!password.matches(passwordRegex)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Invalid credentials.");
        }});
        List<User> userList = userDAO.getUserByEmail(email);
        if(userList.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
            put("err", "Invalid credentials.");
        }});
        User user = userList.get(0);
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if(!passwordEncryptor.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(){{
                put("err", "Invalid credentials.");
            }});
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>() {{
                put("id", user.getId());
                put("fname", user.getFname());
                put("lname", user.getLname());
                put("role", user.getRole());
                put("authToken", getJWT(user.getId(), user.getFname(), user.getLname(), user.getRole()));
            }});
        }
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> getUserFromCookie(AuthCookieRequest authCookieRequest) {
        String authCookie = authCookieRequest.getAuthCookie();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(decodeJWT(authCookie));
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>() {{
                put("err", e.getMessage());
            }});
        }
    }

    private String getJWT(int id, String fname, String lname, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty(JWT_SECRET)));
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", id)
                    .withClaim("fname", fname)
                    .withClaim("lname", lname)
                    .withClaim("role", role)
                    .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                    .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    private HashMap<String, Object> decodeJWT(String authCookie) throws IllegalArgumentException, JWTVerificationException {
        HashMap<String, Object> userInfo = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty(JWT_SECRET)));
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
        DecodedJWT jwt = verifier.verify(authCookie);
        for (Map.Entry<String, Claim> claim : jwt.getClaims().entrySet()) {
            String key = claim.getKey();
            if(key.equals("iss") || key.equals("exp") || key.equals("iat")) continue;
            String value = claim.getValue().toString();
            try {
                int num = Integer.parseInt(value);
                userInfo.put(key, num);
            } catch (NumberFormatException e) {
                userInfo.put(key, claim.getValue().asString());
            }
        }
        return userInfo;
    }
}
