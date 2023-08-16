package com.store.service.web.controllers.users;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountPageController {

    @GetMapping("/account")
    public String getAccountPage(HttpSession session, Model model){
        model.addAttribute("role", session.getAttribute("role"));
        return "general/account";
    }


}
