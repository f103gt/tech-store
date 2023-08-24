package com.store.controllers.users;

import com.store.dto.contact.info.UserContactInfoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleOptionController {
    @GetMapping("/option")
    public String chooseLoginRole(HttpSession session, Model model) {
        UserContactInfoDTO user = (UserContactInfoDTO) session.getAttribute("user");
        model.addAttribute("roles", session.getAttribute("roles"));
        return "manager/option";
    }

    @PostMapping("/process-option")
    public String processLoginOption(@RequestParam("role") String selectedRole, HttpSession session) {
        session.setAttribute("role", selectedRole);
        return "redirect:/" + selectedRole;
    }
}
