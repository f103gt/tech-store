package com.store.service.web.dto.products;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class SpecificProductDTO {
    private String sku;
    private Integer quantity;
    private BigDecimal price;

    /*private MultipartFile primaryImage;*/
    private MultipartFile[] images;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /*public MultipartFile getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(MultipartFile primaryImage) {
        this.primaryImage = primaryImage;
    }*/

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
