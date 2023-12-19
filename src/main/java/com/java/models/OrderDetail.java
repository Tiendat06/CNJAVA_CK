package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "order_details")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id
    @Column(name = "ORDER_ID")
    private String order_id;
    @Column(name = "PRODUCT_ID")
    private String product_id;
    @Column(name = "QUANTITY")
    private int quantity;
}
