package com.store.repository.products;

import com.store.model.product.Category;
import com.store.model.product.Image;
import com.store.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, BigInteger> {
    Product getProductByProductName(String productName);


    List<Product> getProductsByCategory(Category Category);

    @Query("select p from Product p join p.category c" +
            " where c.categoryName = :categoryName")
    List<Product> getProductsByCategoryName(@Param("categoryName")String categoryName);

    @Query("select p.category from Product p where p.productName = :productName ")
    Category findCategoryByProductName(@Param("productName")String productName);

    void removeAllByCategory(Category category);

    @Query("select p.images from Product p where p.productName = :productName")
    List<Image> findImagesByProductName(@Param("productName") String productName);
}
