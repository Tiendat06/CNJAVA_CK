package com.java.controllers.ChainOfResponsibility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import java.io.IOException;

public interface LoginHandler {
    void setNextHandler(LoginHandler nextHandler);
    void handleRequest(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException;
}

