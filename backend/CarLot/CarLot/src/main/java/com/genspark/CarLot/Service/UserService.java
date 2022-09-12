package com.genspark.CarLot.Service;

import antlr.Token;
import com.genspark.CarLot.Request.*;
import com.genspark.CarLot.Request.AuthRequests.LoginUserRequest;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Request.UserRequests.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;

public interface UserService {

    ResponseEntity<HashMap<String, Object>> registerUser(RegisterUserRequest registerUserRequest);
    ResponseEntity<HashMap<String, Object>> loginUser(LoginUserRequest loginUserRequest);
    ResponseEntity<HashMap<String, Object>> getUserInfo(TokenRequest tokenRequest);
    ResponseEntity<HashMap<String, Object>> updateFname(UpdateFnameRequest updateFnameRequest);
    ResponseEntity<HashMap<String, Object>> updateLname(UpdateLnameRequest updateLnameRequest);
    ResponseEntity<HashMap<String, Object>> updateEmail(UpdateEmailRequest updateEmailRequest);
    ResponseEntity<HashMap<String, Object>> getCloudinaryConfig(TokenRequest tokenRequest);
    ResponseEntity<HashMap<String, Object>> savePfpRef(PfpRefRequest pfpRefRequest);
    ResponseEntity<HashMap<String, Object>> deletePfp(TokenRequest tokenRequest) throws IOException;
}
