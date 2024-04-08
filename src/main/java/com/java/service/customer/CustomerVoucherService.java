package com.java.service.customer;

import com.java.repository.CustomerVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerVoucherService {
    @Autowired
    private CustomerVoucherRepository customerVoucherRepository;

    public String totalCustomerVoucherUsed(String cus_id){
        return customerVoucherRepository.totalCustomerVoucherUsed(cus_id);
    }

    public String AUTO_CSV_ID(){
        String maxID = customerVoucherRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("CSV%07d", number);
        }
        return "CSV0000001";
    }
}
