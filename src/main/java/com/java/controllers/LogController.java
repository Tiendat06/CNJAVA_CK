package com.java.controllers;


import com.java.models.Account;
import com.java.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private AccountRepository accountRepository;

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

//    @GetMapping("/logout")
//    public void logout_GET(HttpServletResponse resp) throws IOException {
//        resp.sendRedirect("/");
//    }
        @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
        }

    @GetMapping("/verify")
    @ResponseBody
    public String verifyAccount(@Param("code") String code, Model m) {
        Account account = accountRepository.findByVerifyCode(code);
        if (account==null)
            return "Not found";

//        if (f) {
//            m.addAttribute("msg", "Sucessfully your account is verified");
//        } else {
//            m.addAttribute("msg", "May be your vefication code is incorrect or already veified ");
//        }

//        return "message";
        accountRepository.updateStatusAndVerifyCode(account.getAccount_id());
        return "Verify successful" + account.toString();
    }


}
