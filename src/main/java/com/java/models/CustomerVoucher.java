package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@Entity(name = "customers_voucher")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVoucher {

    @Id
    @Column(name = "CUSTOMER_VOUCHER_ID")
    private String customer_voucher_id;
    @Column(name = "VOUCHER_ID")
    private int voucher_id;
    @Column(name = "CUSTOMER_ID")
    private String customer_id;
    @Column(name = "DATE_USED")
    private Date date_used;
}
