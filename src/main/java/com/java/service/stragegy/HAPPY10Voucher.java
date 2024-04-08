package com.java.service.stragegy;

public class HAPPY10Voucher implements IStragegy{
    @Override
    public double calculateVoucher(double price){
        return (price * 10 / 100) + price;
    }
}
