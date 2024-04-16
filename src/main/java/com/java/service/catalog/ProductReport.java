package com.java.service.catalog;

import com.java.models.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ProductReport {

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<byte[]> exportProductReport(List<Product> productList) {
        byte[] excelBytes = createExcelBytes(productList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product_data.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelBytes);
    }

    private byte[] createExcelBytes(List<Product> productList) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Product Data");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Category", "Description", "Quantity", "Original Price", "Retail Price", "Date Created"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (Product product : productList) {
                Row row = sheet.createRow(rowNum++);
                String categoryName = categoryService.getCategoryNameByCategoryId(product.getCategory_id()).getName();
                row.createCell(0).setCellValue(product.getProduct_id());
                row.createCell(1).setCellValue(product.getProduct_name());
                row.createCell(2).setCellValue(categoryName);
                row.createCell(3).setCellValue(product.getDescription());
                row.createCell(4).setCellValue(product.getQuantity_stock());
                row.createCell(5).setCellValue(product.getProduct_price());
                row.createCell(6).setCellValue(product.getRetail_price());
                row.createCell(7).setCellValue(product.getDate_created().toString());
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
