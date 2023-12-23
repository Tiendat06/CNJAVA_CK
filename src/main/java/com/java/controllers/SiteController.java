package com.java.controllers;

import com.java.models.*;
import com.java.service.CustomerService;
import com.java.service.MyUserDetailsService;
import com.java.service.OrdersService;
import com.java.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Security;
import java.sql.Timestamp;

@Controller
@RequestMapping("")
public class SiteController implements ErrorController {

    @Autowired
    public ProductService productService;
    @Autowired
    public OrdersService ordersService;
    @Autowired
    public CustomerService customerService;
    @GetMapping("")
    public String index(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        model.addAttribute("content", "home");

        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (myUserDetail.getCombinedUser().getAccount().isTemp_pass()) {
            myUserDetail.getCombinedUser().getAccount().setStatus(false);
            return "redirect:/log/change_pass";
        }
        return "redirect:/1";
    }

    @GetMapping("/{pageNo}")
    public String homeProductPagination(@PathVariable int pageNo,
                                        @RequestParam(defaultValue = "9") int pageSize, Model model){
        model.addAttribute("content", "home");
        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);

        model.addAttribute("productList", productList);

        return "index";

    }

    @GetMapping("/home/category/{id}")
    public String getProductCategory(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "9") int pageSize,
                                     @PathVariable int id, Model model){
//        model.addAttribute("content", "home");
        System.out.println("hi");
        Page<Object[]> productList = productService.getAllProductPaginationOrderByCategory(pageNo - 1, pageSize, id);
        for (Object[] item:
             productList) {
            System.out.println(item[0]);
        }
        model.addAttribute("productList", productList);
        return "/home/category_search";
    }

    @PostMapping("/add/product")
    public void addProductToOrderList(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("product_id");
        String quantity = req.getParameter("quantity");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        ordersService.orderRepository.save(new Order("", "USE0000001", null, "", ""));

    }

    @PostMapping("/add/customer")
    public void addCustomerInHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = customerService.AUTO_CUS_ID();
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone-add");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        customerService.customerRepository.save(new Customer(id, fullname, address, phone, email, timestamp));

        resp.sendRedirect("/");

    }

    @GetMapping("/find/{phone_number}")
    public String findCusByPhone(@PathVariable String phone_number, Model model){
//        System.out.println("hiiii");
//        System.out.println(phone_number);
        Customer customer = customerService.findCusByPhone(phone_number);
        model.addAttribute("customer", customer);
        return "/home/find_cus_by_phone";
    }

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest req){
        model.addAttribute("code", req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.addAttribute("url", req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        model.addAttribute("msg", req.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        return "/error/error";
    }

    @GetMapping("/verified")
    public String verified(){

        return "/log/verified";
    }
}
