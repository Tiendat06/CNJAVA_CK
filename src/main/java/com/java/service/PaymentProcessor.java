package com.java.service;

import org.springframework.web.servlet.view.RedirectView;

@FunctionalInterface
public interface PaymentProcessor {
    RedirectView processPayment(double amount);
}