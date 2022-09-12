package com.genspark.CarLot.Request.UserRequests;

import lombok.Getter;

@Getter
public class UpdateEmailRequest {
    private String accessToken;
    private String email;
}
