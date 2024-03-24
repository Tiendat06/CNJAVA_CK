package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public class InvalidCredentialsHandler implements LoginHandler {
    @Override
    public void setNextHandler(LoginHandler nextHandler) {
        // This is the last handler, so no need to set the next handler
    }

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp, Model model) {
        model.addAttribute("error", "Invalid Username or Password!");
    }
}
