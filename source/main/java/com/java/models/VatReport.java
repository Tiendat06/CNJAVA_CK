package com.java.models;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


@XmlRootElement(name = "vat-report")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class VatReport {

    @XmlElement(name = "timestamp")
    private Timestamp timestamp;

    @XmlElement(name = "customer-name")
    private String cusName;

    @XmlElement(name = "pos-first-name")
    private String posFirstName;

    @XmlElement(name = "pos-last-name")
    private String posLastName;

    @XmlElement(name = "status")
    private String status;

    @XmlElement(name = "order-id")
    private String orderId;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<VatReportItem> items;

    // Optional: Add a field for total amount
    private double totalAmount;
}

