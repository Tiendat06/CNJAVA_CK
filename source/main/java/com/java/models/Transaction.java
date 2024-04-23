package com.java.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "transaction")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(name = "TRANSACTION_ID")
    private String transaction_id;
    @Column(name = "PAYMENT_ID")
    private String payment_id;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "ORDER_ID")
    private String order_id;
}
