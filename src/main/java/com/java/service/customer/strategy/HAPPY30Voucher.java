package com.java.service.customer.strategy;

public class HAPPY30Voucher implements IStrategy {
    @Override
    public double calculateVoucher(double price) {
        return price - (price * 30 / 100);
    }
}
