package com.java.controllers;

import com.java.models.Account;
import com.java.models.MyUserDetail;
import com.java.models.User;
import com.java.repository.AccountRepository;
import com.java.service.AccountService;
import com.java.service.RoleService;
import com.java.service.UserService;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    public AccountService accountService;

    @Autowired
    public RoleService roleService;
    @Autowired
    public UserService userService;
    @Autowired
    public AccountRepository accountRepository;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "account");
        return "redirect:/account/1";
    }

    @GetMapping("/{pageNo}")
    public String accountPagination(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        model.addAttribute("content", "account");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        Page<Object[]> accList = accountService.getAccPagination(pageNo - 1, pageSize);
        model.addAttribute("accList", accList);

        return "index";
    }

    @GetMapping("/{accID}/{roleName}")
    public void changeRole(@PathVariable String accID, @PathVariable String roleName, HttpServletResponse resp) throws IOException {
        int roleId = roleService.getRoleByRoleName(roleName).getRole_id();

        accountService.updateRoleByAccId(accID, roleId);
        resp.sendRedirect("/account/1");

    }

    @GetMapping("/lock/{accID}/{status}")
    public void changeStatus(@PathVariable String accID, @PathVariable boolean status, HttpServletResponse resp) throws IOException {
        boolean newStatus = true;
        if (status) newStatus = false;

//        System.out.println("hiii");
        accountService.updateStatusByAccId(accID, newStatus);
        resp.sendRedirect("/account/1");

    }
    
    @GetMapping("/sendEmail/{accID}")
    public void resendEmail(@PathVariable String accID, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        User user = userService.userRepository.findUserByAccount_id(accID);
        String url = req.getRequestURL().toString();
        url = url.replace(req.getServletPath(), "");

        String verify_code = accountRepository.findVerifyCodeByUserId(user.getUser_id());
        String[] parts = verify_code.split("-",2);
        String uuid = parts.length >= 2 ? parts[1] : null;
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        verify_code = formattedDateTime + "-" + uuid;
        accountRepository.updateVerifyCode(user.getAccount_id(),verify_code);

        userService.sendEmail(user,url);
        resp.sendRedirect("/account/1");

    }


}
