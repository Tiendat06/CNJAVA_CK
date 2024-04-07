package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpSession;

public interface PasswordHandler {
    boolean handleRequest(String password, String confirmPassword, HttpSession session);
    void setNextHandler(PasswordHandler nextHandler);
}
