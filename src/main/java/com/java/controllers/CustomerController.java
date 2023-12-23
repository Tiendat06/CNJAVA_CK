package com.java.controllers;

import com.java.models.Customer;
import com.java.service.CustomerService;
import com.java.service.OrderDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    public CustomerService customerService;

    @Autowired
    public OrderDetailsService orderDetailsService;

    @GetMapping("")
    public String index(Model model, HttpSession session){

        model.addAttribute("content", "customer");
        return "redirect:/customer/1";
    }

    @GetMapping("/{pageNo}")
    public String getCustomerPagination(@PathVariable int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize, Model model){
        model.addAttribute("content", "customer");
        Page<Customer> customerList = customerService.getCustomersPagination(pageNo - 1, pageSize);
        model.addAttribute("customerList", customerList);

//        model.addAttribute("purchaseList", new CustomerUtil());

        return "index";
    }

    @GetMapping("/purchase-history/{cusId}")
    public String getPurchaseHistoryByCusId_GET(@PathVariable String cusId, Model model){
        List<Object[]> purchaseList = orderDetailsService.getPurchaseHistoryListByCustomerId(cusId);
        model.addAttribute("purchaseList", purchaseList);
        return "/customer/purchase_history";
    }

    @GetMapping("/order_details/{cusId}/{date}")
    public String getPurchaseHistoryInDetailsByCusId_GET(@PathVariable String cusId, @PathVariable Timestamp date, Model model){
        List<Object[]> purchaseList = orderDetailsService.getPurchaseHistoryDetailsByCusId(cusId, date);
        model.addAttribute("purchaseList", purchaseList);
        return "/customer/purchase_history_details";
    }


}
