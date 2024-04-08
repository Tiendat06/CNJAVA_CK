package com.java.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer_voucher")
public class CustomerVoucherController {

    @GetMapping("/add_voucher/AJAX")
    public String addCustomerVoucher(@RequestParam("phone_number") String phone,
                                     @RequestParam("voucher_id") String voucher_id){
//        String phone = req.getParameter("phone_number");
//        String voucher_id = req.getParameter("voucher_id");
        System.out.println(phone);
        System.out.println(voucher_id);
        return "/home/addCustomerVoucher";
    }
}
