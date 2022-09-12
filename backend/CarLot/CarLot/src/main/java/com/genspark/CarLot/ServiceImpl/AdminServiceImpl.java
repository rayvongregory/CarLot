package com.genspark.CarLot.ServiceImpl;

import com.genspark.CarLot.DAO.CarDAO;
import com.genspark.CarLot.Request.AdminRequests.NumNewRequestsRequest;
import com.genspark.CarLot.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CarDAO carDAO;

    @Override
    public ResponseEntity<HashMap<String, Object>> getNumNewRequests(NumNewRequestsRequest numNewRequestsRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(){
            {
                put("numNewRequests", carDAO.getNumNewRequests());
            }
        });
    }
}
