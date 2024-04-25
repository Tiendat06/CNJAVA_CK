package com.java.service.catalog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.java.models.Product;
import com.java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public  ProductReport productReport;

    public Page<Product> getAllProductPagination(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findAll(pageable);
    }

    public Page<Object[]> getAllProductPaginationOrderByCategory(int pageNo, int pageSize, int categoryId){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.getAllProductOrderByCategory(pageable, categoryId);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public ResponseEntity<byte[]> exportProductData(List<Product> productList){

        return productReport.exportProductReport(productList);
    }

    public Page<Product> searchInHome(int pageNo, int pageSize, String character){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.searchInHome(character, pageable);
    }

    public String AUTO_PRO_ID(){
        String maxID = productRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("PRO%07d", number);
        }
        return "PRO0000001";
    }

    public void addProduct(Product p){
        productRepository.save(p);
    }

    public void updateProduct(String id, Product p){
        productRepository.updateProduct(id, p);
    }

    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }

    public byte[] convertBarCodeImgToByte(String id){
        BufferedImage img = generateBarCode(id);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public BufferedImage generateBarCode(String id){
        Code128Writer code128Writer = new Code128Writer();
        BitMatrix bitMatrix = code128Writer.encode(id, BarcodeFormat.CODE_128, 300, 150);
        return generateBarCodeToImg(bitMatrix);
    }

    public BufferedImage generateBarCodeToImg(BitMatrix bitMatrix){
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return img;
    }

}
