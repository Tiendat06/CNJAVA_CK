package com.java.controllers.Decorative;

import com.java.models.Product;
import com.java.models.User;
import com.opencsv.CSVWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NormalExport implements Export {
    @Override
    public byte[] export(List<Product> productList) {
        return generateCSVData(productList);
    }

    private byte[] generateCSVData(List<Product> productList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8))) {
            writer.writeNext(new String[]{"Product name", "Import price", "Retail price", "Category", "Quantity", "Date Created", "Description","Barcode"});
            for (Product product : productList) {
                String[] userData = {product.getProduct_name(), String.valueOf(product.getProduct_price()), String.valueOf(product.getRetail_price()), String.valueOf(product.getCategory_id()),
                        String.valueOf(product.getQuantity_stock()), String.valueOf(product.getDate_created()), String.valueOf(product.getDescription()), String.valueOf(product.getBarcode())};
                writer.writeNext(userData);
            }
        } catch (IOException e) {
            // Handle the exception, e.g., log it or return an error response
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
