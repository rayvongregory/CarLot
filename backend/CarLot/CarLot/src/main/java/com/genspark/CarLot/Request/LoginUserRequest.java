package com.genspark.CarLot.Request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUserRequest {
    private String email;
    private String password;
}
