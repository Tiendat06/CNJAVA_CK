package com.java.controllers;

import com.java.models.Customer;
import com.java.service.CustomerService;
import com.java.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    public CustomerService customerService;

    @Autowired
    public OrderDetailsService orderDetailsService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "customer");
        return "redirect:/customer/1";
    }

    @GetMapping("/{pageNo}")
    public String getCustomerPagination(@PathVariable int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize, Model model){
        model.addAttribute("content", "customer");
        Page<Customer> customerList = customerService.getCustomersPagination(pageNo - 1, pageSize);
        model.addAttribute("customerList", customerList);

        model.addAttribute("purchaseList", new CustomerUtil());

        return "index";
    }

    @GetMapping("/purchase-history/{cusId}")
    public String getPurchaseHistoryByCusId(@PathVariable String cusId, Model model){
        List<Object[]> purchaseList = orderDetailsService.getPurchaseHistoryListByCustomerId(cusId);
        model.addAttribute("purchaseList", purchaseList);
        return "/customer/purchase_history";
    }

    @GetMapping("/order_details/{cusId}")
    public String getPurchaseHistoryInDetailsByCusId(@PathVariable String cusId, Model model){
        List<Object[]> purchaseList = orderDetailsService.getPurchaseHistoryDetailsByCusId(cusId);
        model.addAttribute("purchaseList", purchaseList);
        return "/customer/purchase_history_details";
    }

    public class CustomerUtil{
        public List<Object[]> getPurchaseHistory(String cus_id){
            return orderDetailsService.getPurchaseHistoryDetailsByCusId(cus_id);
        }
    }
}
