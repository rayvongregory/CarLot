package com.genspark.CarLot.Request;

import lombok.Getter;

@Getter
public class RegisterUserRequest {
    private String fname;
    private String lname;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;


}
