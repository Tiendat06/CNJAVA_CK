package com.java.service.strategy;

public class HAPPY10Voucher implements IStrategy {
    @Override
    public double calculateVoucher(double price, int discount){
        return price - (price * discount / 100);
    }
}
