package com.java.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String user_id;
    @Column(name = "FIRST_NAME")
    private String first_name;
    @Column(name = "LAST_NAME")
    private String last_name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phone_number;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "ACCOUNT_ID")
    private String account_id;
    @Column(name = "BIRTHDAY")
    private Date birthday;
    @Column(name = "GENDER")
    private String gender;
}
