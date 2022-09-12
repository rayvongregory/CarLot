package com.genspark.CarLot.Request.UserRequests;

import lombok.Getter;

@Getter
public class PfpRefRequest {
    private String accessToken;
    private String publicId;
    private String url;
    private String assetId;
}
