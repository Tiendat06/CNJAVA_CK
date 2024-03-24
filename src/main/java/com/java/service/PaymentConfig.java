//package com.java.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class PaymentConfig {
//    @Autowired
//    public PaypalProcessor paypalProcessor;
//    @Autowired
//    public CashProcessor cashProcessor;
//
//    @Bean
//    public Map<String, PaymentProcessor> paymentProcessors() {
//        Map<String, PaymentProcessor> map = new HashMap<>();
//        map.put("paypal", paypalProcessor);
//        map.put("cash", cashProcessor);
//        // Add more entries for other payment processors
//        return map;
//    }
//}
