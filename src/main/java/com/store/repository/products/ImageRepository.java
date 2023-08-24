package com.store.repository.products;

import com.store.model.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    Image getImageById(BigInteger id);
}
