package com.store.dao.repository.products;

import com.store.dao.model.product.Image;
import com.store.dao.model.product.SpecificProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    List<Image> findAllByProduct(SpecificProduct specificProduct);
    List<Image> findAllBySpecificProduct(SpecificProduct specificProduct);
    Image getImageById(BigInteger id);
}
