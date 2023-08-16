package com.store.dao.repository.products;

import com.store.dao.model.product.Category;
import com.store.dao.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, BigInteger> {
    Product getProductByProductName(String productName);

    List<Product> getProductsByCategory(Category Category);

    void removeAllByCategory(Category category);
}
