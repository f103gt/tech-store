package com.store.validation.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private final HttpServletRequest request;

    @Autowired
    public TokenManager(HttpServletRequest request) {
        this.request = request;
    }

    public void storeTokenInSession(String token){
        HttpSession session = request.getSession();
        session.setAttribute("emailVerificationToken",token);
        session.setMaxInactiveInterval(900);
    }
    public String getTokenFromSession(){
        HttpSession session = request.getSession();
        return (String) session.getAttribute("emailVerificationToken");
    }

    public void removeTokenFromSession(){
        HttpSession session = request.getSession();
        session.removeAttribute("emailVerificationToken");
    }
}
