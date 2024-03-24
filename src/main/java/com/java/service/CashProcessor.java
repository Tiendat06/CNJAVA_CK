package com.java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
public class CashProcessor implements PaymentProcessor{
    @Override
    public RedirectView processPayment(double amount) {
        System.out.println("Processing cash payment for $" + amount);
        return new RedirectView("http://localhost:8080/site/payment_success"); // Assuming successful transaction
    }
}
