package com.java.service.customer.strategy;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class VoucherStrategy {
    private IStrategy iStrategy;
//    public VoucherStrategy(){}
    public void setStrategy(IStrategy strategy){
        this.iStrategy = strategy;
    }

    public double calculateVoucher(double price){
        return iStrategy.calculateVoucher(price);
    }
}
