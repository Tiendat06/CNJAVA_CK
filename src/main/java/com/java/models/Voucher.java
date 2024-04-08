package com.java.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "voucher")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VOUCHER_ID")
    private int voucher_id;
    @Column(name = "VOUCHER_NAME")
    private String voucher_name;
    @Column(name = "VOUCHER_DISCOUNT")
    private String voucher_discount;

}
