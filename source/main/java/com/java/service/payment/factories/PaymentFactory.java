package com.java.service.payment.factories;

import com.java.service.payment.processors.CashProcessor;
import com.java.service.payment.processors.PaymentProcessor;
import com.java.service.payment.processors.PaypalProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentFactory {

    @Autowired
    public PaypalProcessor paypalProcessor;
    @Autowired
    public CashProcessor cashProcessor;

    public PaymentProcessor getPaymentProcessor(String method) {
        Map<String, PaymentProcessor> processors = new HashMap<>();
        processors.put("paypal", paypalProcessor);
        processors.put("cash", cashProcessor);
        PaymentProcessor processor = processors.get(method);
        if (processor == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return processor;
    }
}

