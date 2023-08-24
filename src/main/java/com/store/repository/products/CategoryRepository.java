package com.store.repository.products;

import com.store.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    //Optional<Category> findCategoryByCategoryName(String categoryName);
    Category findCategoryByCategoryName(String categoryName);
    void deleteByCategoryName(String categoryName);
}
