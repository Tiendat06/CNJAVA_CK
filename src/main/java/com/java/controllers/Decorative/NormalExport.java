package com.java.controllers.Decorative;

import com.java.models.User;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NormalExport implements Export {
    @Override
    public byte[] export(List<User> userList) {
        return generateCSVData(userList);
    }

    private byte[] generateCSVData(List<User> userList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8))) {
            writer.writeNext(new String[]{"Full Name", "Phone Number", "Email", "Gender", "Address", "Birthday"});
            for (User user : userList) {
                String[] userData = {user.getFirst_name(), user.getPhone_number(), user.getEmail(), user.getGender(), user.getAddress(), String.valueOf(user.getBirthday())};
                writer.writeNext(userData);
            }
        } catch (IOException e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
