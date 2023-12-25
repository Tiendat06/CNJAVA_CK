package com.java.controllers;

import com.java.models.Account;
import com.java.models.MyUserDetail;
import com.java.models.User;
import com.java.service.AccountService;
import com.java.service.ImageService;
import com.java.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    public AccountService accountService;
    @Autowired
    public ImageService imageService;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "user");

        return "redirect:/user/1";
    }

    @GetMapping("/{pageNo}")
    public String getUserPagination(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        Page<User> userList = userService.getUserPagination(pageNo - 1, pageSize);
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        model.addAttribute("content", "user");
        model.addAttribute("userList", userList);
        return "index";
    }

    @GetMapping("/profile")
    public String user_profile_GET(Model model){
        model.addAttribute("content", "profile");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        List<Object[]> user = userService.getUserProfile(myUserDetail.getCombinedUser().getAccount().getAccount_id());
        List<Object[]> user = userService.getUserProfile(myUserDetail.getCombinedUser().getUser().getUser_id());
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
//        System.out.println(user.toString());
        model.addAttribute("userInfo", user);

        return "index";
    }

    @PostMapping("/profile/update")
    public void user_profile_update_GET(@RequestParam("fileImg") MultipartFile fileImg, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        String id = req.getParameter("user_id");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String gender = req.getParameter("gender");
        Date birthday = Date.valueOf(req.getParameter("date"));
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        User user1 = userService.getAccIDByUserID(id);
        String imgName = "user_profile.png";

        if (!user1.getImage().equals(imgName) && fileImg.isEmpty()){
            imgName = id + ".png";
        }
        if (!fileImg.isEmpty()){
            byte[] img = fileImg.getBytes();
            imageService.saveImage(img, id+".png");
            imgName = id+".png";
        }

        User user = userService.getAccIDByUserID(id);
        String acc_id = user.getAccount_id();

        userService.updateProfile(id, new User(id, firstname, lastname, email, phone, address, imgName, acc_id, birthday, gender));
        resp.sendRedirect("/user/profile");
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

        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        accountService.accountRepository.save(new Account(acc_id, false, true, true, 2, pwd, formattedDateTime + "-" + UUID.randomUUID()));

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
        String acc_id = user.getAccount_id();

        userService.deleteByID(user_id);
        accountService.deleteByID(acc_id);
//        System.out.println(user_id);
//        System.out.println(acc_id);
        resp.sendRedirect("/user/1");
    }
}