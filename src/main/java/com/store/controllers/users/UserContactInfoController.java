package com.store.controllers.users;

import com.store.dto.contact.info.UserContactInfoDTO;
import com.store.services.UserService;
import com.store.validation.email.EmailValidation;
import com.store.validation.otp.OTPGenarator;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserContactInfoController {
    private final UserService userService;
    private final EmailValidation emailValidation;
    private final OTPGenarator otpGenarator;

    //TODO remove annotation checker for email existence and make it as a regular check with an error
    //TODO in order to later on reuse email validation in UserContactInfoDTO

    @Autowired
    public UserContactInfoController(UserService userService,EmailValidation emailValidation,OTPGenarator otpGenarator) {
        this.userService = userService;
        this.emailValidation = emailValidation;
        this.otpGenarator = otpGenarator;
    }

    @GetMapping("/contact-info")
    public String getContactInfo(Model model, HttpSession session) {
        UserContactInfoDTO user = (UserContactInfoDTO) session.getAttribute("user");
        model.addAttribute("user",user);
        UserContactInfoDTO contactInfo = new UserContactInfoDTO();
        model.addAttribute("contactInfo", contactInfo);
        return "general/contact-information";
    }

    @PostMapping("/process-contact-information")
    public String processContactInfo(@Valid @ModelAttribute("contactInfo") UserContactInfoDTO contactInfo,
                                     Model model, HttpSession session,BindingResult result) {
        if(result.hasErrors()){
            return "general/contact-information";
        }
        UserContactInfoDTO user = (UserContactInfoDTO) session.getAttribute("user");
        String newEmail = contactInfo.getEmail();
        String newPhone = contactInfo.getPhone();
        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            String otp = otpGenarator.generateOTP(6);
            session.setAttribute("otp",otp);
            session.setAttribute("newEmail",newEmail);
            emailValidation.otpEmailValidation("Enter the following code to confirm your email: ", newEmail, otp);
            return "redirect:/tech-store.com/change-email";
        }
        if (newPhone != null && !newPhone.equals(user.getPhone())) {
            user.setPhone(contactInfo.getPhone());
            return "redirect:/tech-store.com/change-phone";
        }
        /*userService.updateUserInfo(user, contactInfo);*/
        userService.updateUserInfo((String) session.getAttribute("id"), contactInfo);
        return "redirect:/tech-store.com/account";
    }

    @GetMapping("/change-email")
    public String changeEmail(HttpSession session, Model model) {
        String newEmail = (String) session.getAttribute("newEmail");
        String otp = "";
        model.addAttribute("email",newEmail);
        model.addAttribute("otp",otp);
        return "general/otp-email";
    }

    //TODO resend otp and set the timer for otp to be valid for 15 minutes
    @PostMapping("/process-email-otp")
    public String processEmailOTP(Model model, HttpSession session,
                                  @RequestParam("otp") String otp,
                                  BindingResult result) {
        String originalOtp = (String) session.getAttribute("otp");
        session.removeAttribute("otp");
        if(result.hasErrors()){
            return "general/otp-email";
        }
        if(!otp.equals(originalOtp)){
            result.addError(new ObjectError("otp", "Entered code is invalid!"));
            return "general/otp-email";
        }
        UserContactInfoDTO user = (UserContactInfoDTO) session.getAttribute("user");
        user.setEmail((String) session.getAttribute("email"));
        session.removeAttribute("newEmail");
        userService.updateUserInfo((String) session.getAttribute("id"), user);
        return "redirect:/tech-store.com/account";
    }

    @GetMapping("/change-phone")
    public String changePhone(HttpSession session, Model model) {
        model.addAttribute("role", session.getAttribute("role"));
        return "general/otp-phone";
    }
}
