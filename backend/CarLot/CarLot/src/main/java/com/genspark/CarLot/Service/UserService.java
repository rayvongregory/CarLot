package com.genspark.CarLot.Service;

import com.genspark.CarLot.Request.AuthCookieRequest;
import com.genspark.CarLot.Request.LoginUserRequest;
import com.genspark.CarLot.Request.RegisterUserRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface UserService {

    public ResponseEntity<HashMap<String, Object>> registerUser(RegisterUserRequest registerUserRequest);
    public ResponseEntity<HashMap<String, Object>> loginUser(LoginUserRequest loginUserRequest);
    public ResponseEntity<HashMap<String, Object>> getUserFromCookie(AuthCookieRequest authCookieRequest);

}
