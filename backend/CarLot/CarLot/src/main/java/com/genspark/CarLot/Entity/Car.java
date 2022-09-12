package com.genspark.CarLot.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Car")
public class Car {

    public enum Status {
        UNDER_REVIEW, REJECTED, LISTED;
    }

    @Id
    @Column(name = "VIN")
    private String vin;

    @Column(name = "UserId")
    private String userId;

    @Column(name = "Make", nullable = false)
    private String make;

    @Column(name = "Model", nullable = false)
    private String model;

    @Column(name = "Year", nullable = false)
    private int year;

    @Column(name = "Mileage", nullable = false)
    private int mileage;

    @Column(name = "FuelType", nullable = false)
    private String fuelType;

    @Column(name = "Drivetrain")
    private String drivetrain;

    @Column(name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

}
