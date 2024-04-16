package com.java.service.user.adapter;

import com.java.models.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class XLSXReport implements IXLSXReport {

    @Override
    public ResponseEntity<byte[]> exportReportOldMethod(List<User> userList) {
        byte[] excelBytes = createExcelBytes(userList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_data.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }

    private byte[] createExcelBytes(List<User> userList) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("User Data");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "First name", "Last name", "Email", "Phone number", "Address", "Birthday", "Gender"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (User user : userList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getUser_id());
                row.createCell(1).setCellValue(user.getFirst_name());
                row.createCell(2).setCellValue(user.getLast_name());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getPhone_number());
                row.createCell(5).setCellValue(user.getAddress());
                row.createCell(6).setCellValue(user.getBirthday().toString());
                row.createCell(7).setCellValue(user.getGender());
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
