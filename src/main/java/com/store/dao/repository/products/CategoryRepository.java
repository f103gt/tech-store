package com.store.dao.repository.products;

import com.store.dao.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    //Optional<Category> findCategoryByCategoryName(String categoryName);
    Category findCategoryByCategoryName(String categoryName);
    void deleteByCategoryName(String categoryName);
}
