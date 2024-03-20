package com.java.service.proxy;

import com.java.models.Customer;
import com.java.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyCustomerService implements ICustomerService{
    @Autowired
    private CustomerService customerService;

    @Override
    public String addCustomer(Customer customer) {
//        if (isValidCustomer(customer).equals("Success")){
//            return customerService.addCustomer(customer);
//        }
        return isValidCustomer(customer).equals("Success") ? customerService.addCustomer(customer): isValidCustomer(customer);
    }

    private String isValidCustomer(Customer customer){
        if (customer.getCustomer_name().isEmpty() || customer.getCustomer_email().isEmpty()
                || customer.getCustomer_address().isEmpty() || customer.getCustomer_phone().isEmpty()){
            return "You must fill in all fields !";
        } else if (!customer.getCustomer_email().contains("@")){
            return "Invalid email format !";
        } else if (customerService.findCusByPhone(customer.getCustomer_phone()) != null) {
            return "Phone is already existed !";
        }
        return "Success";
    }


}
