package com.java.controllers.Decorative;

import com.java.models.Product;
import com.java.models.User;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressDecorator extends ExportDecorator{
    public CompressDecorator(Export wrapObj) {
        super(wrapObj);
    }

    @Override
    public byte[] export(List<Product> productList) {
        // Call the export method of the decorated object (BasicExport)
        byte[] exportedData = wrapObj.export(productList);
//        byte[] compressedData = compress(exportedData, userList);
        return compress(exportedData);
    }
    private byte[] compress(byte[] data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry("product_report.csv");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(data); // Write the exported data directly to the zip output stream
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

//    private byte[] compress(byte[] data, List<User> userList) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
//            ZipEntry zipEntry = new ZipEntry("user_report.csv");
//            zipOutputStream.putNextEntry(zipEntry);
//            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(zipOutputStream, StandardCharsets.UTF_8);
//                 CSVWriter writer = new CSVWriter(outputStreamWriter)) {
//
//                // Writing header
//                writer.writeNext(new String[]{"Full Name", "Phone Number", "Email", "Gender", "Address", "Birthday"});
//
//                // Writing user data
//                for (User user : userList) {
//                    String[] userData = {user.getFirst_name(), user.getPhone_number(), user.getEmail(), user.getGender(), user.getAddress(), String.valueOf(user.getBirthday())};
//                    writer.writeNext(userData);
//                }
//
//                // Flush and close the writer (this also closes the underlying OutputStream)
//                writer.flush();
//                zipOutputStream.closeEntry();
//            }
//        } catch (IOException e) {
//            // Handle the exception, e.g., log it or return an error response
//            e.printStackTrace();
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
    }
