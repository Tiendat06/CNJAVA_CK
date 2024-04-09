package com.java.models;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@XmlRootElement(name = "vat-report")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class VatReport {

    @XmlElement(name = "order-id")
    private String orderId;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<VatReportItem> items;
    // Optional: Add a field for total amount
    private double totalAmount;

    // Optional: Add a method to calculate total amount (assuming price is in item)
    public void calculateTotalAmount() {
        totalAmount = items.stream().mapToDouble(VatReportItem::getChangeGiven).sum();
    }
}
