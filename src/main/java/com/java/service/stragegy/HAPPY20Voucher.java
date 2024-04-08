package com.java.service.stragegy;

public class HAPPY20Voucher implements IStragegy{
    @Override
    public double calculateVoucher(double price) {
        return (price * 20 / 100) + price;
    }
}
