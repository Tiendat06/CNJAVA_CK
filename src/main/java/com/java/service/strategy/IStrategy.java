package com.java.service.strategy;

@FunctionalInterface
public interface IStrategy {
    double calculateVoucher(double price, int discount);
}
