package com.java.service.customer;

import com.java.models.CustomerVoucher;
import com.java.repository.CustomerVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerVoucherService {
    @Autowired
    private CustomerVoucherRepository customerVoucherRepository;

    public String totalCustomerVoucherUsed(String cus_id){
        return customerVoucherRepository.totalCustomerVoucherUsed(cus_id);
    }

    public void addCustomerVoucher(CustomerVoucher customerVoucher){
        customerVoucherRepository.save(customerVoucher);
    }

    public Optional<CustomerVoucher> findCustomerVoucherByCusIdAndVoucherId(String cus_id, int voucher_id){
        return customerVoucherRepository.findCustomerVoucherByCusIdAndVoucherId(cus_id, voucher_id);
    }

    public void updateCustomerVoucherInUsed(CustomerVoucher customerVoucher){
        customerVoucherRepository.updateCustomerVoucherDateUsed(customerVoucher);
    }

    public String getCustomerVoucherId(String cus_id){
        return customerVoucherRepository.getCustomerVoucherId(cus_id);
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
