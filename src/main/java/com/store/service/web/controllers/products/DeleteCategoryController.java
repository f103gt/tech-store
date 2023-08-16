package com.store.service.web.controllers.products;

import com.store.dao.model.product.Category;
import com.store.dao.model.product.Image;
import com.store.dao.repository.products.CategoryRepository;
import com.store.dao.repository.products.ImageRepository;
import com.store.dao.repository.products.ProductRepository;
import com.store.dao.repository.products.SpecificProductRepository;
import com.store.service.web.dto.products.category.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.PrimitiveIterator;

@Controller
public class DeleteCategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SpecificProductRepository specificProductRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public DeleteCategoryController(CategoryRepository categoryRepository,
                                    ProductRepository productRepository,
                                    SpecificProductRepository specificProductRepository,
                                    ImageRepository imageRepository) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.specificProductRepository = specificProductRepository;
        this.imageRepository = imageRepository;
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
