package com.store.controllers.products;

import com.store.model.product.Category;
import com.store.repository.products.CategoryRepository;
import com.store.repository.products.ProductRepository;
import com.store.dto.products.category.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeleteCategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DeleteCategoryController(CategoryRepository categoryRepository,
                                    ProductRepository productRepository) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/delete-category")
    public String deleteCategory(Model model){
        model.addAttribute("category",new CategoryDTO());
        return "manager/category/delete-category";
    }
    @PostMapping("/process-delete-category")
    @Transactional
    public String processDeleteCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO){
        String categoryName = categoryDTO.getCategoryName();
        Category category = categoryRepository.findCategoryByCategoryName(categoryName);
        productRepository.removeAllByCategory(category);
        categoryRepository.deleteByCategoryName(categoryName);
        return "redirect:/manager";
    }
}
