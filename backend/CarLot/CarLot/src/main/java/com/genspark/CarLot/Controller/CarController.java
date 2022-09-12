package com.genspark.CarLot.Controller;

import com.genspark.CarLot.Request.CarRequests.ListCarRequest;
import com.genspark.CarLot.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping(value = "/api/list-car")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> listCar(@RequestBody ListCarRequest listCarRequest) {
        return carService.listCar(listCarRequest);
    }

}
