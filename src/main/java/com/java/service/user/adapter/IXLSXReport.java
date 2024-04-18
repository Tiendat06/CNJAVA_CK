package com.java.service.user.adapter;

import com.java.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FunctionalInterface
public interface IXLSXReport {
    ResponseEntity<byte[]> exportReportXLSXMethod(List<User> userList);
}
