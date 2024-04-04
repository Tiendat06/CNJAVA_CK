package com.java.service.payment.strategy;

public class TaxCal{

    private final TaxStrategy taxStrategy;

    public TaxCal(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double CalculateTax(double cost) {
        return taxStrategy.Cal(cost);
    }

    public static void main(String[] args) {
        double productCost = 100;

        // Không thuế
        TaxStrategy noTaxStrategy = new NoTax();
        TaxCal noTaxCalculator = new TaxCal(noTaxStrategy);
        double noTaxAmount = noTaxCalculator.CalculateTax(productCost);
        System.out.println("Thuế (không thuế): " + noTaxAmount);

        // Thuế VAT ( 10% )
        TaxStrategy basicTaxStrategy = new VATCal();
        TaxCal basicTaxCalculator = new TaxCal(basicTaxStrategy);
        double basicTaxAmount = basicTaxCalculator.CalculateTax(productCost);
        System.out.println("Thuế VAT ( 10% ): " + basicTaxAmount);
    }
}
