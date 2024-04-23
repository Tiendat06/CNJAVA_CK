package com.java.service.order;

import com.java.models.OrderDetail;
import com.java.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    public OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetail> getAllOrderDetails(String id){

        return orderDetailsRepository.checkProduct(id);
    }

    public List<Object[]> getPurchaseHistoryDetailsByCusId(String id, Timestamp date){
        return orderDetailsRepository.getPurchaseHistoryDetailsByCustomerId(id, date);
    }

    public List<Object[]> getPurchaseHistoryListByCustomerId(String id){
        return orderDetailsRepository.getPurchaseHistoryListByCustomerId(id);
    }

//    public void deleteAllProductByOrderId(String ord_id){
//        orderDetailsRepository.deleteAllProductByOrderId(ord_id);
//    }

    public String AUTO_ODT_ID(){
        String maxID = orderDetailsRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("ODT%07d", number);
        }
        return "ODT0000001";
    }


}
