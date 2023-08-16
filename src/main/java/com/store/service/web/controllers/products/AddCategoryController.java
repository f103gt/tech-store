package com.store.service.web.controllers.products;

import com.store.dao.model.product.Category;
import com.store.dao.repository.products.CategoryRepository;
import com.store.service.web.dto.products.category.CategoryDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddCategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public AddCategoryController(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/add-category")
    public String addCategoryDto(Model model, HttpSession session) {
        model.addAttribute("category", new CategoryDTO());
        return "manager/category/add-category";
    }

    @PostMapping("/process-add-category")
    public String processAddCategory(
            @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            return "manager/category/add-category";
        }
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());;
        categoryRepository.save(category);
        return "redirect:/manager";
    }
    //TODO handle the situation when batch insertion is not completed, yet the manager wants to insert
    //TODO child categories right after inserting parent category without pressing "complete" button
    // message: "To insert child categories, you need to complete insertion of parent ones,"
    //           "press complete button after adding parent category and repeat the process for children"
}
