package com.genspark.CarLot.ServiceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.genspark.CarLot.DAO.CarDAO;
import com.genspark.CarLot.Entity.Car;
import com.genspark.CarLot.Request.CarRequests.ListCarRequest;
import com.genspark.CarLot.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDAO carDAO;

    @Override
    public ResponseEntity<HashMap<String, Object>> listCar(ListCarRequest listCarRequest) {
        String accessToken = listCarRequest.getAccessToken();
        DecodedJWT decodedJWT = JWT.decode(accessToken);
        String id = decodedJWT.getClaim("id").asString();
        String role = decodedJWT.getClaim("role").asString();
        Car car = new Car();
        car.setUserId(id);
        String VIN = getRandomVin();
        car.setVin(VIN);
        car.setMake(listCarRequest.getMake());
        car.setModel(listCarRequest.getModel());
        car.setYear(listCarRequest.getYear());
        car.setMileage(listCarRequest.getMileage());
        car.setFuelType(listCarRequest.getFuelType());
        car.setDrivetrain(listCarRequest.getDrivetrain());
        if(role.equals("admin")) car.setStatus(Car.Status.LISTED);
        else car.setStatus(Car.Status.UNDER_REVIEW);
        carDAO.save(car);
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("msg", "Request to list car with id: %s was made.".formatted(VIN));
            }
        });
    }

    private String getRandomVin() {
        Random r = new Random();
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder VIN = new StringBuilder();
        while(VIN.length() < 17) {
            int lOn = r.nextInt(2);
            if(lOn == 0) VIN.append(letters[r.nextInt(letters.length)]);
            else VIN.append(numbers[r.nextInt(numbers.length)]);
        }
        try {
            carDAO.findById(VIN.toString()).get();
            return getRandomVin();
        } catch (NoSuchElementException e) {
            return VIN.toString();
        }
    }
}
