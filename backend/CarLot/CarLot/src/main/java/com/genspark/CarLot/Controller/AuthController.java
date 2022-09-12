package com.genspark.CarLot.Controller;

import com.genspark.CarLot.Request.AuthRequests.LoginUserRequest;
import com.genspark.CarLot.Request.RegisterUserRequest;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Service.TokenService;
import com.genspark.CarLot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AuthController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/register-user")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        return userService.registerUser(registerUserRequest);
    }

    @PostMapping(value = "/api/login-user")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        return userService.loginUser(loginUserRequest);
    }

    @PostMapping(value = "/api/verify-token")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> verifyToken(@RequestBody TokenRequest tokenRequest) {
        return tokenService.verifyToken(tokenRequest);
    }


}
