package com.java.service.stragegy;

import org.springframework.stereotype.Service;

@Service
public class VoucherStragegy {
    private IStragegy iStragegy;
    public void setStragegy(IStragegy tax){
        this.iStragegy = tax;
    }

    public double calculateTax(double price){
        return iStragegy.calculateVoucher(price);
    }
}
