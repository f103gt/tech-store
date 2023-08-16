package com.store.dao.repository.products;

import com.store.dao.model.product.SpecificProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SpecificProductRepository extends JpaRepository<SpecificProduct, BigInteger> {
    SpecificProduct findBySku(String sku);
}
