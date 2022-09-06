package com.genspark.CarLot.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class RegisterUserRequest {
    private String fname;
    private String lname;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;


}
