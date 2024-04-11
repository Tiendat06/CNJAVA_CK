package com.java.service.user.adapter;

import com.java.models.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.util.List;

@FunctionalInterface
public interface INewReport {
    ResponseEntity<byte[]> exportReportNewMethod(List<String[]> userList);
}
