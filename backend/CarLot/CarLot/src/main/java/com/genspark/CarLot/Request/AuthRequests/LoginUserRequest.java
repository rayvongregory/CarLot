package com.genspark.CarLot.Request.AuthRequests;

import lombok.Getter;

@Getter
public class LoginUserRequest {
    private String email;
    private String password;
}
