package com.store.service.web.controllers.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RestController
public class HomePageController {

    @GetMapping(value ="/")
    public String homePage() {
        return "general/home";
    }
    @GetMapping(value ={"/user","/manager","/admin"})
    public String homePageAuthorized() {
        return "general/home";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }
}
