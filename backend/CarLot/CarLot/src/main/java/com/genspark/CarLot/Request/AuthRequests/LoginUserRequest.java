package com.genspark.CarLot.Request.AuthRequests;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginUserRequest {
    private String email;
    private String password;
}
