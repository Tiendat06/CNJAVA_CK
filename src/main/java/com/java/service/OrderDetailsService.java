package com.java.service;

import com.java.models.OrderDetail;
import com.java.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    public OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetail> getAllOrderDetails(String id){

        return orderDetailsRepository.checkProduct(id);
    }

    public List<Object[]> getPurchaseHistoryDetailsByCusId(String id){
        return orderDetailsRepository.getPurchaseHistoryDetailsByCustomerId(id);
    }

    public List<Object[]> getPurchaseHistoryListByCustomerId(String id){
        return orderDetailsRepository.getPurchaseHistoryListByCustomerId(id);
    }
}
