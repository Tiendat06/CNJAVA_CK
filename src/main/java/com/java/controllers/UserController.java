package com.java.controllers;

import com.java.controllers.Decorative.CompressDecorator;
import com.java.controllers.Decorative.Export;
import com.java.controllers.Decorative.NormalExport;
import com.java.service.user.builder.IUserBuilder;
import com.java.service.user.builder.UserBuilder;
import com.java.models.Account;
import com.java.models.MyUserDetail;
import com.java.models.User;
import com.java.service.account.AccountService;
import com.java.service.image.ImageService;
import com.java.service.user.UserService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
                                    @RequestParam(defaultValue = "10") int pageSize, Model model) throws IOException {
        Page<User> userList = userService.getUserPagination(pageNo - 1, pageSize);
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        ADAPTER PATTERN (TTD)
        List<String> provinceAPI = userService.getProvinceAPI();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        model.addAttribute("content", "user");
        model.addAttribute("userList", userList);
        model.addAttribute("provinceAPI", provinceAPI);

        return "index";
    }

    @GetMapping("/{pageNo}/ajax")
    public String getUserPagination_AJAX(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        Page<User> userList = userService.getUserPagination(pageNo - 1, pageSize);
//        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
//        model.addAttribute("content", "user");
        model.addAttribute("userList", userList);
        return "/user/user_list";
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

        model.addAttribute("update_error", "");
        model.addAttribute("add_success", "");

        return "index";
    }

    @PostMapping("/profile/update")
    public void user_profile_update_GET(@RequestParam("fileImg") MultipartFile fileImg, HttpServletResponse resp, HttpServletRequest req, Model model) throws IOException {
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Object[]> user_info = userService.getUserProfile(myUserDetail.getCombinedUser().getUser().getUser_id());
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        model.addAttribute("userInfo", user_info);

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

//        check null or empty
        if (id.isEmpty() || firstname.isEmpty() || lastname.isEmpty()
                || gender.isEmpty() || birthday.toString().isEmpty()
                || email.isEmpty() || address.isEmpty() || phone.isEmpty()){
            model.addAttribute("update_error", "You must fill in all fields");
            System.out.println("Hi world !");
            resp.sendRedirect("/user/profile");
        } else if (!email.contains("@")) {
            model.addAttribute("update_error", "Invalid email format");
            resp.sendRedirect("/user/profile");
        }
        if (!user1.getImage().equals(imgName) && fileImg.isEmpty()){
            imgName = id + ".png";
//            if (!user1.getImage().equals(imgName) && fileImg.isEmpty()){
//                imgName = id + ".png";
//            }
        }
        if (!fileImg.isEmpty()){
            byte[] img = fileImg.getBytes();
            imageService.saveImage(img, id+".png");
            imgName = id+".png";
        }

        User user = userService.getAccIDByUserID(id);
        String acc_id = user.getAccount_id();
        model.addAttribute("add_success", "Update Successfully !!");

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

//            User user = new User(maxID, firstname, lastname, email, phone, address, "user_profile.png", acc_id, date, gender);

//        BUILDER PATTERN (TTD)
        IUserBuilder userBuilder = new UserBuilder()
                                    .setUserIdBuilder(maxID)
                                    .setFirstNameBuilder(firstname)
                                    .setLastNameBuilder(lastname)
                                    .setEmailBuilder(email)
                                    .setPhoneNumberBuilder(phone)
                                    .setAddressBuilder(address)
                                    .setImgBuilder("user_profile.png")
                                    .setAccIdBuilder(acc_id)
                                    .setBirthdayBuilder(date)
                                    .setGenderBuilder(gender);

        User user = userBuilder.build();
        userService.addUser(user);
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
//        ADAPTER PATTERN
        User user = userService.getAccIDByUserID(user_id);
        String acc_id = user.getAccount_id();

        userService.deleteByID(user_id);
        accountService.deleteByID(acc_id);
//        System.out.println(user_id);
//        System.out.println(acc_id);
        resp.sendRedirect("/user/1");
    }

        @GetMapping("/export")
    public ResponseEntity<byte[]> exportUser_POST(HttpServletResponse resp, HttpServletRequest req) throws IOException {
//        System.out.println("hello world");
        List<User> userList = userService.getAllUser();
        String fileType = req.getParameter("id-export");
        if (!fileType.isEmpty()){
            return userService.exportUserReport(userList, fileType, resp);
        }
        resp.sendRedirect("/user");
        return null;
//        resp.sendRedirect("/1");
    }
}