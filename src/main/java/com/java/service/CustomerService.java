package com.java.service;

import com.java.models.Customer;
import com.java.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    public CustomerRepository customerRepository;

    public Page<Customer> getCustomersPagination(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return customerRepository.findAll(pageable);
    }

    public Customer findCusByPhone(String phone){
        return customerRepository.findCusByPhone(phone);
    }

    public String AUTO_CUS_ID(){
        String maxID = customerRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("CUS%07d", number);
        }
        return "CUS0000001";
    }
}
