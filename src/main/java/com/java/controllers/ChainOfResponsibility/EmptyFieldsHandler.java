package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import java.io.IOException;

public class EmptyFieldsHandler implements LoginHandler {
    private LoginHandler nextHandler;

    @Override
    public void setNextHandler(LoginHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");

        if (username == null || pwd == null || username.equals("") || pwd.equals("")) {
            model.addAttribute("error", "Username or Password is empty!");
            return;
        }

        if (nextHandler != null) {
            try {
                nextHandler.handleRequest(req, resp, model);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
