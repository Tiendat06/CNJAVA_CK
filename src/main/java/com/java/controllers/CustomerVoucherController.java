package com.java.controllers;

import com.java.models.Customer;
import com.java.service.customer.CustomerService;
import com.java.service.customer.CustomerVoucherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer_voucher")
public class CustomerVoucherController {
    @Autowired
    public CustomerService customerService;
    @Autowired
    public CustomerService.OrdersService ordersService;
    @Autowired
    public CustomerVoucherService customerVoucherService;

//    @GetMapping("/add_voucher/AJAX")
//    public String addCustomerVoucher(@RequestParam("phone_number") String phone,
//                                     @RequestParam("voucher_id") String voucher_id, Model model){
////        String phone = req.getParameter("phone_number");
////        String voucher_id = req.getParameter("voucher_id");
//        System.out.println(phone);
//        System.out.println(voucher_id);
//
//        Customer customer = customerService.findCusByPhone(phone);
////        String totalCustomerOrder = ordersService.currentCustomerOrder(customer.getCustomer_id());
////        String totalCustomerVoucherUsed = customerVoucherService.totalCustomerVoucherUsed(customer.getCustomer_id());
////
////        String customerTotalPoint = String.valueOf (Integer.parseInt(totalCustomerOrder) - (int) Double.parseDouble(totalCustomerVoucherUsed));
//
//        if(Integer.parseInt(customerTotalPoint) >= 10 && Integer.parseInt(voucher_id) == 1){
//
//        } else if (Integer.parseInt(customerTotalPoint) >= 20 && Integer.parseInt(voucher_id) == 2) {
//
//        } else if(Integer.parseInt(customerTotalPoint) >= 30 && Integer.parseInt(voucher_id) == 3){
//            model.addAttribute("result", "Add successfully !!");
//        }else{
//            model.addAttribute("result", "Your point is not enough");
//        }
//
//        return "/home/addCustomerVoucher";
//    }
}
