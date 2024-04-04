package com.java.service.payment.strategy;

public class NoTax implements TaxStrategy {

    @Override
    public double Cal(double Cost) {
        return 0;
    }
}
