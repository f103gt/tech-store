package com.store.controllers.general;

import com.store.repository.promotion.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    private final PromotionRepository promotionRepository;

    @Autowired
    public HomePageController(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @GetMapping(value ="/")
    public String homePage() {

        return "general/home";
    }
    @GetMapping(value ={"/user","/manager","/admin"})
    public String homePageAuthorized() {
        return "general/home";
    }

}
