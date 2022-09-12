package com.genspark.CarLot.Service;

import com.genspark.CarLot.Request.AdminRequests.NumNewRequestsRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface AdminService {

    ResponseEntity<HashMap<String, Object>> getNumNewRequests(NumNewRequestsRequest numNewRequestsRequest);
}
