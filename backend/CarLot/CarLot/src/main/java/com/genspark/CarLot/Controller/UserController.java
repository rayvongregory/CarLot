package com.genspark.CarLot.Controller;

import com.genspark.CarLot.Request.AuthRequests.TokenRequest;
import com.genspark.CarLot.Request.UserRequests.PfpRefRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateEmailRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateFnameRequest;
import com.genspark.CarLot.Request.UserRequests.UpdateLnameRequest;
import com.genspark.CarLot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/get-user-info")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getUserInfo(@RequestBody TokenRequest tokenRequest) {
        return userService.getUserInfo(tokenRequest);
    }

    @PatchMapping(value = "/api/update-fname")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> updateFname(@RequestBody UpdateFnameRequest updateFnameRequest) {
        return userService.updateFname(updateFnameRequest);
    }

    @PatchMapping(value = "/api/update-lname")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> updateLname(@RequestBody UpdateLnameRequest updateLnameRequest) {
        return userService.updateLname(updateLnameRequest);
    }

    @PatchMapping(value = "/api/update-email")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> updateEmail(@RequestBody UpdateEmailRequest updateEmailRequest) {
        return userService.updateEmail(updateEmailRequest);
    }

    @PostMapping(value = "/api/get-cloudinary-config")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> getCloudinaryInfo(@RequestBody TokenRequest tokenRequest ) {
        return userService.getCloudinaryConfig(tokenRequest);
    }

    @PostMapping(value = "/api/save-pfp-ref")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> savePfpRef(@RequestBody PfpRefRequest pfpRefRequest ) {
        return userService.savePfpRef(pfpRefRequest);
    }

    @PostMapping(value = "/api/delete-user-pfp")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> deleteUserPfp (@RequestBody TokenRequest tokenRequest) throws IOException {
       return userService.deletePfp(tokenRequest);
    }


}
