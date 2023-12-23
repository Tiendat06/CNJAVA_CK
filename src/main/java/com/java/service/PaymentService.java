package com.java.service;

import com.java.models.Payment;
import com.java.models.Product;
import com.java.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    public PaymentRepository paymentRepository;

    public Float getTotalPaymentAmount(){
        return paymentRepository.totalAmount();
    }


}
