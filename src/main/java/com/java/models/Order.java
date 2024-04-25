package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "orders")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "ORDER_ID")
    private String order_id;
    @Column(name = "USER_ID")
    private String user_id;
    @Column(name = "DATE_CREATED")
    private Timestamp date_created;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "CUSTOMER_ID")
    private String customer_id;
}
