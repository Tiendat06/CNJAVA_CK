package com.java.service.user.adapter;

import org.springframework.http.ResponseEntity;

import java.util.List;

@FunctionalInterface
public interface ICSVReport {
    ResponseEntity<byte[]> exportReportCSVMethod(List<String[]> userList);
}
