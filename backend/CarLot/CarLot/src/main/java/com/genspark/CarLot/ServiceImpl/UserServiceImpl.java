package com.genspark.CarLot.ServiceImpl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.genspark.CarLot.DAO.UserDAO;
import com.genspark.CarLot.DAO.UserPfpDAO;
import com.genspark.CarLot.Entity.User;
import com.genspark.CarLot.Entity.UserPfp;
import com.genspark.CarLot.Request.*;
import com.genspark.CarLot.Request.AuthRequests.LoginUserRequest;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Request.UserRequests.*;
import com.genspark.CarLot.Service.TokenService;
import com.genspark.CarLot.Service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserPfpDAO userPfpDAO;
    @Autowired
    private Environment env;
    private final String emailRegex = "^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$";
    private final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    private final String JWT_SECRET = "secret.keys.jwt";
    private final String CLOUD_NAME="secret.name.cloudinary";
    private final String CLOUD_SECRET="secret.cloudinary";
    private final String CLOUD_KEY="secret.keys.cloudinary.api";
    private final String UPLOAD_PRESET="cloudinary.uploadpreset";
    private final String CLOUDINARY_FOLDER="cloudinary.folder";

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
            user.setId(UUID.randomUUID().toString());
            user.setFname(fname);
            user.setLname(lname);
            user.setEmail(email);
            user.setLocked(false);
            user.setVerified(true); // set true for now, set to false when email verification is set up
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
                put("accessToken", generateAccessToken(user.getId(), user.getRole()));
            }});
        }
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> getUserInfo(TokenRequest tokenRequest) {
        String accessToken = tokenRequest.getAccessToken();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        User user = userDAO.findById(id).orElse(null);
        if(user == null) return null;
        List<UserPfp> pfpList = userPfpDAO.getPfpByUserId(id);

        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("pfp", pfpList.size() == 0 ? "" : pfpList.get(0).getUrl());
                put("fname", user.getFname());
                put("lname", user.getLname());
                put("email", user.getEmail());

            }
        });
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> updateFname(UpdateFnameRequest updateFnameRequest) {
        String accessToken = updateFnameRequest.getAccessToken();
        String fname = updateFnameRequest.getFname();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        User user = userDAO.findById(id).orElse(null);
        if(user == null) return null;
        user.setFname(fname);
        userDAO.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){ {
            put("msg", "First name changed to %s".formatted(fname));
        }});
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> updateLname(UpdateLnameRequest updateLnameRequest) {
        String accessToken = updateLnameRequest.getAccessToken();
        String lname = updateLnameRequest.getLname();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        User user = userDAO.findById(id).orElse(null);
        if(user == null) return null;
        user.setFname(lname);
        userDAO.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){ {
            put("msg", "Last name changed to %s".formatted(lname));
        }});
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> updateEmail(UpdateEmailRequest updateEmailRequest) {
        String accessToken = updateEmailRequest.getAccessToken();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        User user = userDAO.findById(id).orElse(null);



        return null;
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> getCloudinaryConfig(TokenRequest tokenRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("cloudname", env.getProperty(CLOUD_NAME));
                put("uploadpreset", env.getProperty(UPLOAD_PRESET));
                put("folder", env.getProperty(CLOUDINARY_FOLDER));
            }
        });
    }

    @Override
    @Transactional
    public ResponseEntity<HashMap<String, Object>> savePfpRef(PfpRefRequest pfpRefRequest) {
        String accessToken = pfpRefRequest.getAccessToken();
        String publicId = pfpRefRequest.getPublicId();
        String url = pfpRefRequest.getUrl();
        String assetId = pfpRefRequest.getAssetId();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        UserPfp userPfp = new UserPfp();
        userPfp.setUserId(id);
        userPfp.setPublicId(publicId);
        userPfp.setUrl(url);
        userPfp.setAssetId(assetId);
        userPfpDAO.save(userPfp);
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>() {
            {
                put("msg", "User pfp ref saved.");
            }
        });
    }

    @Override
    public ResponseEntity<HashMap<String, Object>> deletePfp(TokenRequest tokenRequest) throws IOException {
        String accessToken = tokenRequest.getAccessToken();
        String id = JWT.decode(accessToken).getClaim("id").asString();
        List<UserPfp> userPfpList = userPfpDAO.getPfpByUserId(id);
        if(userPfpList.size() > 0) {
            UserPfp userPfp = userPfpList.get(0);
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", env.getProperty(CLOUD_NAME),
                    "api_key", env.getProperty(CLOUD_KEY),
                    "api_secret", env.getProperty(CLOUD_SECRET),
                    "secure", true));
            cloudinary.uploader().destroy(userPfp.getPublicId(), new HashMap());
            userPfpDAO.delete(userPfp);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
                {
                    put("publicId", userPfp.getPublicId());
                }
            });
        } else
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("msg", "User had no previous pfp");
            }
        });
    }

    private String generateAccessToken(String id, String role) {
        // the access token and refresh token both hold the user id, role, and refresh token key
        // whenever the access token needs to be verified, simply check that the access token sent with the request matches the access token claim in the refresh token
        // once verified, use the user id claim to proceed with fulfilling the request
        try {
            Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty(JWT_SECRET)));
            String rtk = RandomStringUtils.randomAlphabetic(20);  // refresh token key
            String accessToken = JWT.create()
                    .withClaim("id", id)
                    .withClaim("role", role)
                    .withClaim("rtk", rtk)
                    .withIssuer("auth0")
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(60 * 15)) // 60 * 15
                    .sign(algorithm);
            tokenService.addRefreshToken(rtk, algorithm);
            return accessToken;
        } catch (JWTCreationException exception) {
            return null;
        }
    }
}
