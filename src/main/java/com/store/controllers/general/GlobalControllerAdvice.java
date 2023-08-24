package com.store.controllers.general;

import com.store.model.product.Category;
import com.store.repository.products.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final HttpSession session;
    private final CategoryRepository categoryRepository;

    @Autowired
    public GlobalControllerAdvice(HttpSession session,
                                  CategoryRepository categoryRepository) {
        this.session = session;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute("role")
    public String getUserRole() {
        String role = (String) session.getAttribute("role");
        if(role == null){
            session.setAttribute("role","guest");
            return "guest";
        }
        return role ;
    }

    @ModelAttribute("categories")
    public List<Category> getProductCategories() {
        return categoryRepository.findAll();
    }
}
