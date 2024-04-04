package com.java.service.payment.strategy;

public class VATCal implements TaxStrategy {

    private static final double TAX_RATE = 0.1; // Tỷ lệ thuế cơ bản (10%)

    @Override
    public double Cal(double Cost) {
        return Cost * TAX_RATE; // Áp dụng thuế cơ bản
    }
}
