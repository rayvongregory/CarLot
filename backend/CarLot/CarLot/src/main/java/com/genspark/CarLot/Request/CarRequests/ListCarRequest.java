package com.genspark.CarLot.Request.CarRequests;

import lombok.Getter;

@Getter
public class ListCarRequest {
    private String accessToken;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String fuelType;
    private String drivetrain;
}
