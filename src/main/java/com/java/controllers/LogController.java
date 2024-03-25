package com.java.controllers;


import com.java.controllers.ChainOfResponsibility.AdminCredentialsHandler;
import com.java.controllers.ChainOfResponsibility.EmptyFieldsHandler;
import com.java.controllers.ChainOfResponsibility.InvalidCredentialsHandler;
import com.java.controllers.ChainOfResponsibility.LoginHandler;
import com.java.models.Account;
import com.java.models.MyUserDetail;
import com.java.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/log")
public class LogController {
    private LoginHandler loginHandler;

    public LogController() {
        LoginHandler invalidCredentialsHandler = new InvalidCredentialsHandler();
        LoginHandler adminCredentialsHandler = new AdminCredentialsHandler();
        LoginHandler emptyFieldsHandler = new EmptyFieldsHandler();
        emptyFieldsHandler.setNextHandler(adminCredentialsHandler);
        adminCredentialsHandler.setNextHandler(invalidCredentialsHandler);
        this.loginHandler  = emptyFieldsHandler;
    }

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

//  ROL
    @PostMapping("/login")
    public String login_POST(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        loginHandler.handleRequest(req, resp, model);
        //        String username = req.getParameter("username");
//        String pwd = req.getParameter("password");
//        System.out.println(username);
//        System.out.println(pwd);
//
//
//        if (username.equals("") || pwd.equals("")) {
//            model.addAttribute("error", "Username or Password is empty!");
//        } else if (username == null || pwd == null) {
//            model.addAttribute("error", "Username or Password is empty!");
//        } else if (username.equals("admin") && pwd.equals("admin")) {
//            resp.sendRedirect("/");
//        } else{
//            model.addAttribute("error", "Invalid Username or Password!");
//        }
//
//        System.out.println(username);
//        System.out.println(pwd);
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

        HttpSession session = request.getSession();
        accountRepository.updateStatusAndVerifyCode(account.getAccount_id());
        try {
            String username = accountRepository.findEmailByAccount_id(account.getAccount_id());
            String password = username.split("@")[0];
            request.login(password, password);
            return "redirect:/log/success";
        } catch (Exception exception) {
            return "redirect:/log/fail";
        }

    }

    @GetMapping("/change_pass")
    public String changePass_GET(Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        if (session.getAttribute("password") != null){
            session.removeAttribute("password");
        }
        model.addAttribute("content", "change_pass");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        return "index";
    }

    @PostMapping("/change_pass")
    public String changePass(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String pwd = req.getParameter("new_pass");
        String new_pwd = req.getParameter("re_new_pass");
        HttpSession session = req.getSession();
        if (pwd.isEmpty() || new_pwd.isEmpty()){
            session.setAttribute("password", "You must fill in all fields");
        }
        else if (pwd.length()<6) {
            session.setAttribute("password", "Password must have least 6 characters");
        }
        else if (!pwd.equals(new_pwd)) {
            session.setAttribute("password", "Confirm password didn't match");
        }
        else {
            session.setAttribute("temp_pass", false);
            MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            myUserDetail.getCombinedUser().getAccount().setTemp_pass(false);
            accountRepository.updateTempPass(myUserDetail.getCombinedUser().getAccount().getAccount_id());
            pwd = new BCryptPasswordEncoder().encode(pwd);
            accountRepository.updatePassword(myUserDetail.getCombinedUser().getAccount().getAccount_id(),pwd);
            session.setAttribute("password", "success");
        }
        model.addAttribute("content", "change_pass");
        return "index";
    }

}
