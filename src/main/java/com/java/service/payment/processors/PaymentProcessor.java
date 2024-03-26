package com.java.service.payment.processors;

import org.springframework.web.servlet.view.RedirectView;

@FunctionalInterface
public interface PaymentProcessor {
    RedirectView processPayment(double amount);
}