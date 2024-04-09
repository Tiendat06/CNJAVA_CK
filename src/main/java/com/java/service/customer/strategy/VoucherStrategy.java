package com.java.service.customer.strategy;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class VoucherStrategy {
    private IStrategy iStrategy;
//    public VoucherStrategy(){}
    public void setStrategy(IStrategy price){
        this.iStrategy = price;
    }

    public double calculateVoucher(double price, int discount){
        return iStrategy.calculateVoucher(price, discount);
    }
}
