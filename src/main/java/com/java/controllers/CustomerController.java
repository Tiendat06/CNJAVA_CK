package com.java.controllers;

import com.java.models.Customer;
import com.java.models.MyUserDetail;
import com.java.models.OrderDetail;
import com.java.service.CustomerService;
import com.java.service.OrderDetailsService;
import com.java.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    public OrdersService ordersService;

    @GetMapping("")
    public String index(Model model, HttpSession session){

        model.addAttribute("content", "customer");
        return "redirect:/customer/1";
    }

    @GetMapping("/calculate_money/{moneyGiven}")
    public String calculateCustomerGivenChange(@PathVariable String moneyGiven, Model model){

        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Object[]> totalBillList = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
        float totalBill = (float) totalBillList.get(0)[0];

        float cus_given_change = totalBill - Float.parseFloat(moneyGiven);
        model.addAttribute("cus_given_change", cus_given_change);
        return "/home/cus_given_change";

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
