package com.java.service;

import com.java.models.Customer;
import com.java.repository.CustomerRepository;
import com.java.service.adapter.IProvinceAPI;
import com.java.service.adapter.ProvinceAPIAdapter;
import com.java.service.proxy.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public ProvinceAPIAdapter provinceAPIAdapter;

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

    public List<String> getProvinceAPI(){
        IProvinceAPI iProvinceAPI = new ProvinceAPIAdapter();
        return iProvinceAPI.getProvinceAPI();
    }

    @Override
    public String addCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Success";
    }
}
