package com.genspark.CarLot.Service;

import com.genspark.CarLot.Request.CarRequests.ListCarRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface CarService {

    ResponseEntity<HashMap<String,Object>> listCar(ListCarRequest listCarRequest);
}
