package com.java.controllers;

import com.java.models.Account;
import com.java.models.User;
import com.java.service.AccountService;
import com.java.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    public AccountService accountService;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "user");

        return "index";
    }

    @GetMapping("/{pageNo}")
    public String getUserPagination(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        Page<User> userList = userService.getUserPagination(pageNo - 1, pageSize);
        model.addAttribute("content", "user");
        model.addAttribute("userList", userList);
        return "index";
    }

    @GetMapping("/profile")
    public String user_profile_GET(Model model){
        model.addAttribute("content", "profile");
        return "index";
    }

    @PostMapping("/add")
    public void addUser_POST(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        model.addAttribute("content", "user");
        String maxID = userService.AUTO_USER_ID();
        String acc_id = accountService.AUTO_ACC_ID();
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        Date date = Date.valueOf(req.getParameter("date"));
        String gender = req.getParameter("gender");

        String[] check = email.split("@");
        String pwd = bCryptPasswordEncoder().encode(check[0]);
        System.out.println(pwd);
        accountService.accountRepository.save(new Account(acc_id, false, false, false, 2, pwd, UUID.randomUUID().toString()));

        User user = new User(maxID, firstname, lastname, email, phone, address, "user_profile.png", acc_id, date, gender);
        userService.userRepository.save(user);

        String url = req.getRequestURL().toString();
        url = url.replace(req.getServletPath(), "");
        userService.sendEmail(user, url);

        resp.sendRedirect("/user/1");
    }

    @PostMapping("/edit")
    public void updateUser_POST(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        model.addAttribute("content", "user");
        String user_id = req.getParameter("userIdEdit");
        String firstname = req.getParameter("firstname_edit");
        String lastname = req.getParameter("lastname_edit");
        String email = req.getParameter("email_edit");
        String address = req.getParameter("address_edit");
        String phone = req.getParameter("phone_edit");
        Date date = Date.valueOf(req.getParameter("date_edit"));
        String gender = req.getParameter("gender_edit");

        String maxAccId = accountService.accountRepository.maxID();

        userService.updateUser(user_id, new User(user_id, firstname, lastname, email, phone, address, "user_profile.png", maxAccId, date, gender));
        resp.sendRedirect("/user/1");
    }

    @PostMapping("/delete")
    public void deleteUser_POST(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user_id = req.getParameter("userIdDelete");
        User user = userService.getAccIDByUserID(user_id);

        userService.deleteByID(user_id);
//        System.out.println(user_id);
//        System.out.println(acc_id);
        accountService.deleteByID(user.getAccount_id());
        resp.sendRedirect("/user/1");
    }
}