package com.java.service;

import com.java.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyCustomerService implements ICustomerService{
    @Autowired
    private CustomerService customerService;

    @Override
    public String addCustomer(Customer customer) {
        if (isValidCustomer(customer).equals("Success")){
            return customerService.addCustomer(customer);
        }
        return isValidCustomer(customer);
    }

    private String isValidCustomer(Customer customer){
        if (customer.getCustomer_name().isEmpty() || customer.getCustomer_email().isEmpty()
                || customer.getCustomer_address().isEmpty() || customer.getCustomer_phone().isEmpty()){
            return "You must fill in all fields !";
        } else if (!customer.getCustomer_email().contains("@")){
            return "Invalid email format !";
        }
        return "Success";
    }
}
