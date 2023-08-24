package com.store.controllers.products;

import com.store.model.product.Category;
import com.store.model.product.Promotion;
import com.store.repository.products.CategoryRepository;
import com.store.repository.promotion.PromotionRepository;
import com.store.dto.products.promotion.PromotionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Controller
@RequestMapping("/add-promotion")
public class AddPromotionController {
    private CategoryRepository categoryRepository;
    private PromotionRepository promotionRepository;

    @Autowired
    public AddPromotionController(CategoryRepository categoryRepository, PromotionRepository promotionRepository) {
        this.categoryRepository = categoryRepository;
        this.promotionRepository = promotionRepository;
    }

    @GetMapping
    public String addPromotion(Model model){
        model.addAttribute("promotion",new PromotionDTO());
        return "manager/add-promotion";
    }

    @PostMapping
    public String processAddPromotion(@Valid @ModelAttribute("promotion") PromotionDTO promotionDTO){
        Promotion promotion = new Promotion();
        promotion.setDiscountRate(promotionDTO.getDiscountRate());
        promotion.setStartDate(LocalDateTime.parse(promotionDTO.getStartDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        promotion.setEndDate(LocalDateTime.parse(promotionDTO.getEndDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        Category category = categoryRepository.findCategoryByCategoryName(promotionDTO.getCategoryName());
        if(promotion.getCategories() == null){
            promotion.setCategories(new HashSet<>());
        }
        promotion.getCategories().add(category);
        if(category.getPromotions() == null){
            category.setPromotions(new HashSet<>());
        }
        category.getPromotions().add(promotion);
        promotionRepository.save(promotion);
        return "redirect:/";
    }
}
