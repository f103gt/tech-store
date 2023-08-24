package com.store.services;

import com.store.model.product.Image;
import com.store.model.product.Product;
import com.store.repository.products.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String saveImage(MultipartFile multipartFile, Product product,boolean isPrimary) {
        Image image = new Image();
        image.setProduct(product);
        image.setPrimaryImage(isPrimary);
        try {
            image.setImageData(multipartFile.getBytes());
        } catch (IOException e) {
            e.getStackTrace();
        }
        imageRepository.save(image);
        if (image.getImageData() != null) {
            return "file uploaded successfully: " + multipartFile.getOriginalFilename();
        }
        return "unable to upload file";
    }
}
