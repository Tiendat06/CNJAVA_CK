package com.java.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "payment")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "PAYMENT_ID")
    private String payment_id;
    @Column(name = "PAYMENT_METHOD_ID")
    private String payment_method_id;
    @Column(name = "TRANSACTION_ID")
    private String transaction_id;
    @Column(name = "TOTAL_AMOUNT")
    private float total_amount;
    @Column(name = "CHANGE_GIVEN")
    private float change_given;
    @Column(name = "DATE_CREATED")
    private Timestamp date_created;
}
