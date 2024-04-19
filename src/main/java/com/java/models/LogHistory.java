package com.java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "log_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogHistory {

    @Id
    @Column(name = "LOG_ID")
    private String log_id;
    @Column(name = "USER_ID")
    private String user_id;
    @Column(name = "DATE")
    private Timestamp date;
    @Column(name = "STATUS")
    private String status;
//    @Column(name = "IS_LOGOUT")
//    private int is_logout;
}
