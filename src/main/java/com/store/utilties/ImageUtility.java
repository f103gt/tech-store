package com.store.utilties;

import com.store.model.product.Image;
import com.store.model.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ImageUtility {
    public Set<Image> imageUrlsToImages(List<byte[]> imageBytes, Boolean isPrimary, Product product) {
        return imageBytes.stream()
                .map(bytes -> {
                    Image image = new Image();
                    image.setImageData(bytes);
                    image.setProduct(product);
                    image.setPrimaryImage(isPrimary);
                    return image;
                })
                .collect(Collectors.toSet());
    }
}
