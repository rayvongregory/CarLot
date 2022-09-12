package com.genspark.CarLot.Controller;

import com.genspark.CarLot.Request.AdminRequests.NumNewRequestsRequest;
import com.genspark.CarLot.Service.AdminService;
import com.genspark.CarLot.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/api/get-num-new-requests")
    @ResponseBody
    public ResponseEntity<HashMap<String,Object>> getNumNewRequests(@RequestBody NumNewRequestsRequest numNewRequestsRequest) {
        return adminService.getNumNewRequests(numNewRequestsRequest);
    }
}
