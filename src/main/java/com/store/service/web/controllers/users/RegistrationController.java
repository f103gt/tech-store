package com.store.service.web.controllers.users;

import com.store.service.web.dto.registration.UserEmailRegistrationDto;
import com.store.service.web.dto.registration.UserRegistrationDto;
import com.store.service.web.services.UserService;
import com.store.service.web.validation.email.EmailValidation;
import com.store.service.web.validation.token.VerificationToken;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final VerificationToken tokenVerification;
    private final EmailValidation emailValidation;


    @Autowired
    public RegistrationController(UserService userService,
                                  VerificationToken tokenVerification,
                                  EmailValidation emailValidation) {
        this.userService = userService;
        this.tokenVerification = tokenVerification;
        this.emailValidation = emailValidation;
    }

    @GetMapping("/email-registration")
    public String signUp(Model model) {
        model.addAttribute("userEmail", new UserEmailRegistrationDto());
        return "user.registration/email-registration";
    }

    @PostMapping(value = "/process-email-registration")
    public String processEmailRegistration(@Valid @ModelAttribute("userEmail") UserEmailRegistrationDto userEmail,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return "user.registration/email-registration";
        }
        String token = tokenVerification.generateToken(15, userEmail.getEmail());
        emailValidation.sendEmail("Click the following link to confirm your email: ",userEmail.getEmail(), token);
        return "user.registration/email-confirmation-prompt";
    }

    @GetMapping("/email-confirmation-prompt")
    public String getEmailConfirmationPrompt(Model model, UserEmailRegistrationDto userEmail) {
        model.addAttribute("email", userEmail.getEmail());
        return "user.registration/email-confirmation-prompt";
    }

    @GetMapping("/email-confirmation")
    public String getEmailConfirmation(@RequestParam("token") String token,
                                       HttpSession session) {
        try {
            String email = tokenVerification.extractEmail(token);
            tokenVerification.verifyToken(token, email);
            session.setAttribute("email", email);
            return "redirect:/registration";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error-page";
        }
    }


    @GetMapping("/registration")
    public String continueRegistration(@ModelAttribute("user") UserRegistrationDto user,
                                       HttpSession session,
                                       Model model) {
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "user.registration/registration";
    }


    @PostMapping(value = "/process-registration")
    public String processRegistration(@Valid @ModelAttribute("user") UserRegistrationDto user,
                                      BindingResult result,
                                      HttpSession session) {
        if (result.hasErrors()) {
            return "user.registration/registration";
        }
        String email = (String) session.getAttribute("email");
        user.setEmail(email);
        userService.saveUser(user);
        session.removeAttribute("email");
        session.setAttribute("user",user);
        return "redirect:/tech-store.com/user";
    }

}
