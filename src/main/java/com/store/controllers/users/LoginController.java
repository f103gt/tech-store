package com.store.controllers.users;

import com.store.config.security.model.Role;
import com.store.config.security.model.User;
import com.store.config.security.repository.UserRepository;
import com.store.dto.contact.info.UserContactInfoDTO;
import com.store.dto.registration.UserLoginDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller

public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return "general/login";
    }

    @PostMapping("/process-login")
    public String processLogin(@Valid @ModelAttribute("user") UserLoginDTO userLogin,
                               HttpSession session, Model model,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "general/login";
        }
        User user = userRepository.getUserByEmail(userLogin.getEmail());
        if (user == null) {
            result.addError(new ObjectError("user", "User with such email does not exist"));
            return "general/login";
        }
        if (userRepository.getUserByPassword(userLogin.getPassword()) == null) {
            result.addError(new ObjectError("user", "Password is invalid"));
            return "general/login";
        }
        session.setAttribute("user", new UserContactInfoDTO(user));
        session.setAttribute("id", user.getId().toString());
        Set<Role> roles = user.getRoles();
        if (roles.size() == 1) {
            String role = roles.iterator().next().getRole();
            session.setAttribute("role", role);
            return "redirect:/" + role;
        }
        Boolean storeToDatabase = (Boolean) session.getAttribute("storeToDatabase");
        if(storeToDatabase!=null){
            storeToDatabase = true;
            session.setAttribute("storeToDatabase",storeToDatabase);
        }
        session.setAttribute("roles", user.getRoles());
        return "redirect:/option";
    }
}
