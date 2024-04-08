package com.java.service.stragegy;

public class HAPPY30Voucher implements IStragegy{

    @Override
    public double calculateVoucher(double price) {
        return (price * 30 / 100) + price;
    }
}
