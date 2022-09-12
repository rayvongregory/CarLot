package com.genspark.CarLot.Service;

import com.genspark.CarLot.Request.AuthRequests.LoginUserRequest;
import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Request.RegisterUserRequest;
import com.genspark.CarLot.Request.UserRequests.PfpRefRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateEmailRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateFnameRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateLnameRequest;
import org.springframework.http.ResponseEntity;

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
