package com.store.service.web.dto.products;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO implements Serializable {
        private String categoryName;
        private String productName;
        private MultipartFile[] images;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
