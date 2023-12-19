package com.java.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "payment_method")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @Column(name = "payment_method_id")
    private String payment_method_id;
    @Column(name = "payment_name")
    private String payment_name;
}
