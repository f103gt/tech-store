package com.store.utilties;

import com.store.dao.model.product.Image;
import com.store.dao.model.product.Product;
import com.store.dao.model.product.SpecificProduct;
import org.springframework.stereotype.Component;

import java.sql.Blob;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImageUtility {
    public Set<Image> imageUrlsToImages(List<byte[]> imageBytes, SpecificProduct specificProduct, Product product) {
        return imageBytes.stream()
                .map(bytes -> {
                    Image image = new Image();
                    image.setImageData(bytes);
                    image.setProduct(product);
                    image.setSpecificProduct(specificProduct);
                    return image;
                })
                .collect(Collectors.toSet());
    }
}
