package com.genspark.CarLot.Service;

import com.auth0.jwt.algorithms.Algorithm;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface TokenService {

    ResponseEntity<HashMap<String, Object>> verifyToken(TokenRequest tokenRequest);
    void addRefreshToken(String rtk, Algorithm algorithm);
}
