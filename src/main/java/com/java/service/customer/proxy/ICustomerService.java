package com.java.service.customer.proxy;

import com.java.models.Customer;

@FunctionalInterface
public interface ICustomerService {
    String addCustomer(Customer customer);
}
