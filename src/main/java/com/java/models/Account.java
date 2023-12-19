package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import javax.naming.Name;

@Entity(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String account_id;
    @Column(name = "VERITFY")
    private boolean verify;
    @Column(name = "TEMP_PASS")
    private boolean temp_pass;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name = "ROLE_ID")
    private int role_id;
    @Column(name = "PASSWORD")
    private String password;
}
