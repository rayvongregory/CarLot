package com.genspark.CarLot.DAO;

import com.genspark.CarLot.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarDAO extends JpaRepository<Car, String> {
    @Query(value =
            "SELECT COUNT(c) " +
            "FROM Car c " +
            "WHERE c.status='UNDER_REVIEW'")
    int getNumNewRequests();

}
