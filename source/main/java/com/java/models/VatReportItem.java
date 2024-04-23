package com.java.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class VatReportItem {

    @XmlElement(name = "product-name")
    private String productName;

    @XmlElement(name = "quantity")
    private int quantity;

    @XmlElement(name = "change-given")
    private double changeGiven;

    @XmlElement(name = "description")
    private String description;
}
