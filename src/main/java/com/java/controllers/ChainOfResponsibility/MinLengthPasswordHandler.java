package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class MinLengthPasswordHandler implements PasswordHandler {
    private PasswordHandler nextHandler;

    @Override
    public boolean handleRequest(String password, String confirmPassword, HttpSession session) {
        if (password.length() < 6) {
            session.setAttribute("password", "Password must have at least 6 characters");
            return true;
        }
        return nextHandler != null && nextHandler.handleRequest(password, confirmPassword, session);
    }
    @Override
    public void setNextHandler(PasswordHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
