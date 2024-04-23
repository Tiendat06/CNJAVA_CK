package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.sql.Blob;
import java.sql.Timestamp;

@Entity(name = "product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private String product_id;
    @Column(name = "NAME")
    private String product_name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "QTY_STOCK")
    private int quantity_stock;
    @Column(name = "PRICE")
    private float product_price;
    @Column(name = "CATEGORY_ID")
    private int category_id;
    @Column(name = "IMAGE")
    private String product_img;
    @Column(name = "RETAIL_PRICE")
    private float retail_price;
    @Column(name = "DATE_CREATED")
    private Timestamp date_created;
    @Column(name = "barcode")
    private String barcode;

}
