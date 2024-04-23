package com.java.service.customer.strategy;

public class HAPPY20Voucher implements IStrategy {
    @Override
    public double calculateVoucher(double price) {
        return price - (price * 20 / 100);
    }
}
