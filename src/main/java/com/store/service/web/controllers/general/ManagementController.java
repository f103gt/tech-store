package com.store.service.web.controllers.general;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {
    @GetMapping("/management")
    public String management(){
        return "manager/management";
    }
}
