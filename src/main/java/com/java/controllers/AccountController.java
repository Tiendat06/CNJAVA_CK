package com.java.controllers;

import com.java.models.Account;
import com.java.service.AccountService;
import com.java.service.RoleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    public AccountService accountService;

    @Autowired
    public RoleService roleService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "account");

        List<Object[]> accList = accountService.getRoleAndAccId();
        model.addAttribute("accList", accList);
//        model.addAttribute();
        return "index";
    }

    @GetMapping("/{accID}/{roleName}")
    public void changeRole(@PathVariable String accID, @PathVariable String roleName, HttpServletResponse resp) throws IOException {
        int roleId = roleService.getRoleByRoleName(roleName).getRole_id();

        accountService.updateRoleByAccId(accID, roleId);
        resp.sendRedirect("/account");

    }

    @GetMapping("/lock/{accID}/{status}")
    public void changeStatus(@PathVariable String accID, @PathVariable boolean status, HttpServletResponse resp) throws IOException {
        boolean newStatus = true;
        if (status) newStatus = false;

        System.out.println("hiii");
        accountService.updateStatusByAccId(accID, newStatus);
        resp.sendRedirect("/account");

    }

}
