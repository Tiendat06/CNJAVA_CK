package com.java.service.customer.strategy;

public class HAPPY10Voucher implements IStrategy {
    @Override
    public double calculateVoucher(double price){
        return price - (price * 10 / 100);
    }
}
