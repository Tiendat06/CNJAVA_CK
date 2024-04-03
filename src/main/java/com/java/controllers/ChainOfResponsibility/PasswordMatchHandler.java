package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpSession;

public class PasswordMatchHandler implements PasswordHandler {
    private PasswordHandler nextHandler;

    @Override
    public boolean handleRequest(String password, String confirmPassword, HttpSession session) {
        if (!password.equals(confirmPassword)) {
            session.setAttribute("password", "Confirm password didn't match");
            return true;
        }
        return nextHandler != null && nextHandler.handleRequest(password, confirmPassword, session);
    }

    @Override
    public void setNextHandler(PasswordHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
