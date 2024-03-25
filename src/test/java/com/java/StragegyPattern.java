package com.java;

public class StragegyPattern {
    double calculatePrice(Product product);
}
public interface PricingStrategy {
    double calculatePrice(Product product);
}

public class FixedPriceStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Product product) {
        return product.getBasePrice();
    }
}

public class PercentageDiscountStrategy implements PricingStrategy {
    private double discountRate;

    public PercentageDiscountStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double calculatePrice(Product product) {
        return product.getBasePrice() * (1 - discountRate);
    }
}

public class VolumeDiscountStrategy implements PricingStrategy {
    private int minimumQuantity;
    private double discountRate;

    public VolumeDiscountStrategy(int minimumQuantity, double discountRate) {
        this.minimumQuantity = minimumQuantity;
        this.discountRate = discountRate;
    }

    @Override
    public double calculatePrice(Product product) {
        if (product.getQuantity() >= minimumQuantity) {
            return product.getBasePrice() * (1 - discountRate);
        } else {
            return product.getBasePrice();
        }
    }
}

public class Product {
    private String name;
    private double basePrice;
    private PricingStrategy pricingStrategy;
    private int quantity;

    public Product(String name, double basePrice, PricingStrategy pricingStrategy, int quantity) {
        this.name = name;
        this.basePrice = basePrice;
        this.pricingStrategy = pricingStrategy;
        this.quantity = quantity;
    }

    public double getPrice() {
        return pricingStrategy.calculatePrice(this);
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getQuantity() {
        return quantity;
    }
}

