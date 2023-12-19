package com.java.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("")
public class SiteController implements ErrorController {

    @GetMapping("")
    public String index(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        model.addAttribute("content", "home");

//        if (session.getAttribute("username") == null){
////            dispatcher.forward(req, resp);
//            resp.sendRedirect("/log/login");
//        }
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest req){
        model.addAttribute("code", req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.addAttribute("url", req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        model.addAttribute("msg", req.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        return "/error/error";
    }
}
