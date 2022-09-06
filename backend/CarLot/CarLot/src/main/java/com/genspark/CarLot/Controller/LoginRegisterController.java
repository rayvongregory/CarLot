package com.genspark.CarLot.Controller;

import com.genspark.CarLot.Request.AuthCookieRequest;
import com.genspark.CarLot.Request.LoginUserRequest;
import com.genspark.CarLot.Request.RegisterUserRequest;
import com.genspark.CarLot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = {"/api/register-user"})
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        return userService.registerUser(registerUserRequest);
    }

    @PostMapping(value = {"/api/login-user"})
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        return userService.loginUser(loginUserRequest);
    }

    @PostMapping(value = "/api/get-user-info")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getUserFromCookie(@RequestBody AuthCookieRequest cookie, HttpServletRequest request) {
        return userService.getUserFromCookie(cookie);
    }

}
