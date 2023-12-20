package com.java.controllers;

import com.java.models.User;
import com.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "user");
        List<User> userList = (List<User>) userService.userRepository.findAll();
        model.addAttribute("userList", userList);
        return "index";
    }

    @GetMapping("/profile")
    public String user_profile_GET(Model model){
        model.addAttribute("content", "profile");
        return "index";
    }
}
