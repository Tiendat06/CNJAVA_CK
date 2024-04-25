package com.java.service.customer.strategy;

@FunctionalInterface
public interface IStrategy {
    double calculateVoucher(double price);
}
