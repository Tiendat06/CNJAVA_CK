package com.java.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "role")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private int role_id;
    @Column(name = "ROLE")
    private String role_name;
}
