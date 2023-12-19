package com.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "product");
        return "index";
    }

    @GetMapping("/{id}")
    public String getProductCategory(@PathVariable int id, Model model){
        model.addAttribute("content", "home");
        return "index";
    }
}
