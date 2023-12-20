package com.java.controllers;

import com.java.models.Account;
import com.java.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    public AccountService accountService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "account");

//        List<Object[]> accList = accountService.getRoleAndAccId();
//        model.addAttribute("accList", accList);
//        model.addAttribute();
        return "index";
    }


}
