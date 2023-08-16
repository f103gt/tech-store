package com.store.service.web.services;

import com.store.dao.model.product.Image;
import com.store.dao.model.product.Product;
import com.store.dao.model.product.SpecificProduct;
import com.store.dao.repository.products.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String saveImage(MultipartFile multipartFile, Product product, SpecificProduct specificProduct) {
        Image image = new Image();
        image.setProduct(product);
        image.setSpecificProduct(specificProduct);
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
