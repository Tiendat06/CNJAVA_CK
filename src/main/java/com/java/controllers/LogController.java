package com.java.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/log")
public class LogController {

    @GetMapping("/login")
    public String login_GET() {
        return  "/log/login";
    }

    @PostMapping("/login")
    public String login_POST(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");

        if (username.equals("") || pwd.equals("")) {
            model.addAttribute("error", "Username or Password is empty!");
        } else if (username == null || pwd == null) {
            model.addAttribute("error", "Username or Password is empty!");
        } else if (username.equals("admin") && pwd.equals("admin")) {
            resp.sendRedirect("/");
        } else{
            model.addAttribute("error", "Invalid Username or Password!");
        }

        System.out.println(username);
        System.out.println(pwd);
        return "/log/login";
    }

    @GetMapping("/logout")
    public void logout_GET(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/log/login");
    }
}
