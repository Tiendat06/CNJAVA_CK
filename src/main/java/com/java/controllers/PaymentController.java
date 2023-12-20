package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping("/report")
    public String index(Model model){
        model.addAttribute("content", "report");
        return "index";
    }
}
