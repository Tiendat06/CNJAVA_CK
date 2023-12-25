package com.java.service;

import com.java.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    public OrderRepository orderRepository;

    public Page<Object[]> getAllOrdersList(int pageNo, int pageSize, Date date){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return orderRepository.getAllOrderList(pageable, date);
    }

    public List<Object[]> getAllOrderListDetails(String ordId){
        return orderRepository.getOderListDetails(ordId);
    }

    public Page<Object[]> getAllOrderListOrderByDate(int pageNo, int pageSize, Date date_start, Date date_end){
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        return orderRepository.getAllOrderListSortByDatetime(pageable ,date_start, date_end);
    }

    public List<Object[]> getTotalMoneyOrderByDate(Date date_start, Date date_end){
        return orderRepository.totalMoneyOrderByDate(date_start, date_end);
    }

    public List<Object[]> getTotalOrderOrderByDate(Date date_start, Date date_end){
        return orderRepository.totalOrderOrderByDate(date_start, date_end);
    }

    public List<Object[]> getTotalProductOrderByDate(Date date_start, Date date_end){
        return orderRepository.totalProductOrderByDate(date_start, date_end);
    }

    public List<Object[]> getQuanAndPrice(Date date_start, Date date_end){
        return orderRepository.getQuanAndPrice(date_start, date_end);
    }

    public Optional<Object[]> totalBillInHome(String userId){
        return orderRepository.totalBill(userId);
    }

    public List<Object[]> getOrderOfCustomerInHome(String userId){
        return orderRepository.getOrderOfCustomerInHome(userId);
    }

    public String AUTO_ORD_ID(){
        String maxID = orderRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("ORD%07d", number);
        }
        return "ORD0000001";
    }
}
