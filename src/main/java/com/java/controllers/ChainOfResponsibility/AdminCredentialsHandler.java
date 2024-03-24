package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import java.io.IOException;


public class AdminCredentialsHandler implements LoginHandler {
    private LoginHandler nextHandler;

    @Override
    public void setNextHandler(LoginHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");

        if (username.equals("admin") && pwd.equals("admin")) {
            try {
                resp.sendRedirect("/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(req, resp, model);
        }
    }
}
