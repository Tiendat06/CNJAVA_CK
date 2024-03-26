package com.java.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {

//    @Autowired
    public final static String PATH = "src/main/resources/static/img/";
    public void saveImage(byte[] img, String fileName) throws IOException {
        String filePath = PATH + "/" + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(img);
        }
    }
}
