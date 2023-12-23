package com.java.controllers;


import com.java.models.Account;
import com.java.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/login")
    public String login_GET() {
        return  "/log/login";
    }

    @GetMapping("/success")
    public String success_GET() {
        return "/log/verified";
    }
    @GetMapping("/fail")
    public String fail_GET() {
        return "/log/failed";
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
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model m, HttpServletRequest request) {
        Account account = accountRepository.findByVerifyCode(code);
        if (account==null)
            return "redirect:/log/fail";

        LocalDateTime parsedDateTime = LocalDateTime.parse(code.split("-")[0], DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Duration duration = Duration.between(parsedDateTime, LocalDateTime.now());
        long secondsDifference = Math.abs(duration.getSeconds());

        if (secondsDifference > 60){
            return "redirect:/log/fail";
        }

        accountRepository.updateStatusAndVerifyCode(account.getAccount_id());
        try {
            String username = accountRepository.findEmailByAccount_id(account.getAccount_id());
            String password = username.split("@")[0];
            request.login(username, password);
            return "redirect:/log/success";
        } catch (Exception exception) {
            return "redirect:/log/fail";
        }

    }

    @GetMapping("/change_pass")
    public String changePass_GET(Model model){
        model.addAttribute("content", "change_pass");
        return "index";
    }

}
