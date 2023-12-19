package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "user");
        return "index";
    }

    @GetMapping("/profile")
    public String user_profile_GET(Model model){
        model.addAttribute("content", "profile");
        return "index";
    }
}
