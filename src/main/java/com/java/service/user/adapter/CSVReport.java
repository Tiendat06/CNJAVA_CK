package com.java.service.user.adapter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReport implements ICSVReport {

    @Override
    public ResponseEntity<byte[]> exportReportCSVMethod(List<String[]> userListData) {

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"ID", "First Name", "Last Name", "Email", "Phone Number", "Address", "Birthday", "Gender"});

        data.addAll(userListData);

        byte[] csvBytes = convertCsvToBytes(data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_data.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvBytes);
    }

    private byte[] convertCsvToBytes(List<String[]> data) {
        StringBuilder csvContent = new StringBuilder();
        for (String[] rowData : data) {
            for (String cellData : rowData) {
                csvContent.append(cellData).append(",");
            }
            csvContent.deleteCharAt(csvContent.length() - 1).append("\n");
        }
        return csvContent.toString().getBytes();
    }
}
