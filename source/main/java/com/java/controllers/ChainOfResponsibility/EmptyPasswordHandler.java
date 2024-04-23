package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpSession;

public class EmptyPasswordHandler implements PasswordHandler {
    private PasswordHandler nextHandler;

    @Override
    public boolean handleRequest(String password, String confirmPassword, HttpSession session) {
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            session.setAttribute("password", "You must fill in all fields");
            return true;
        }
        return nextHandler != null && nextHandler.handleRequest(password, confirmPassword, session);
    }

    @Override
    public void setNextHandler(PasswordHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
