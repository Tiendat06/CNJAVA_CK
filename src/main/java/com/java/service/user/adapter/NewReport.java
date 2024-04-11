package com.java.service.user.adapter;

import com.java.models.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewReport implements INewReport{

    @Override
    public ResponseEntity<byte[]> exportReportNewMethod(List<String[]> userListData) {

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
