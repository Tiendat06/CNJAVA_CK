package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@Entity(name = "user_voucher")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVoucher {

    @Id
    @Column(name = "USER_VOUCHER_ID")
    private String user_voucher_id;
    @Column(name = "VOUCHER_ID")
    private int voucher_id;
    @Column(name = "USER_ID")
    private String user_id;
    @Column(name = "DATE_USED")
    private Date date_used;
}
