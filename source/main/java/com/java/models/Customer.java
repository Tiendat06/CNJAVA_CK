package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

@Entity(name = "customers")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String customer_id;
    @Column(name = "NAME")
    private String customer_name;
    @Column(name = "ADDRESS")
    private String customer_address;
    @Column(name = "PHONE_NUMBER")
    private String customer_phone;
    @Column(name = "EMAIL")
    private String customer_email;
    @Column(name = "date_created")
    private Timestamp date_created;
}
