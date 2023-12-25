//package com.java.controllers;
//
//import com.java.models.*;
//import com.java.service.*;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.data.domain.Page;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.security.Security;
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("")
//public class SiteController implements ErrorController {
//
//    @Autowired
//    public ProductService productService;
//    @Autowired
//    public OrdersService ordersService;
//    @Autowired
//    public CustomerService customerService;
//    @Autowired
//    public OrderDetailsService orderDetailsService;
//    @GetMapping("")
//    public String index(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        HttpSession session = req.getSession();
//        model.addAttribute("content", "home");
//        model.addAttribute("userImg");
//
//        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (myUserDetail.getCombinedUser().getAccount().isTemp_pass()) {
//            myUserDetail.getCombinedUser().getAccount().setStatus(false);
//            return "redirect:/log/change_pass";
//        }
//        return "redirect:/1";
//    }
//
//    @GetMapping("/remove/product")
//    public String removeOrderInMain(){
//
//        return "index";
//    }
//
//    @GetMapping("/{pageNo}")
//    public String homeProductPagination(@PathVariable int pageNo,
//                                        @RequestParam(defaultValue = "9") int pageSize, Model model){
//        model.addAttribute("content", "home");
//        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);
//
//        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        List<Object[]> orderListCus = ordersService.getOrderOfCustomerInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
//
//        List<Object[]> totalBill = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
//
//        model.addAttribute("orderListCus", orderListCus);
//        model.addAttribute("productList", productList);
//        model.addAttribute("totalBill", totalBill);
//
//        return "redirect:/";
//
//    }
//
//    @GetMapping("/home/category/{id}")
//    public String getProductCategory(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "9") int pageSize,
//                                     @PathVariable int id, Model model){
////        model.addAttribute("content", "home");
//        System.out.println("hi");
//        Page<Object[]> productList = productService.getAllProductPaginationOrderByCategory(pageNo - 1, pageSize, id);
//        for (Object[] item:
//             productList) {
//            System.out.println(item[0]);
//        }
//        model.addAttribute("productList", productList);
//        return "/home/category_search";
//    }
//
////  pending, need to test
//    @PostMapping("/add/product")
//    public String addProductToOrderList(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
//
//        String pro_id = req.getParameter("product_id");
//        System.out.println(pro_id);
//        int quantity = Integer.parseInt(req.getParameter("quantity-ord"));
//        String phone = req.getParameter("phone_number_quan");
//        System.out.println(phone);
//        if (phone == null){
//            model.addAttribute("no_input_phone", "Please enter customer phone number first");
//            return "redirect:/1";
//        }
//
//        String userId = myUserDetail.getCombinedUser().getUser().getUser_id();
//
//        String odt_id = orderDetailsService.AUTO_ODT_ID();
//
//        String max_ord_id = ordersService.orderRepository.maxID();
//        Optional<Order> orderOptional = ordersService.orderRepository.checkUserIdIsNull(max_ord_id);
//
////        String user_id_max_id = ordersService.orderRepository.findById(max_ord_id).get().getUser_id();
//
//        if (orderOptional.isPresent()){
//            String ord_id = ordersService.AUTO_ORD_ID();
//            Customer customer = customerService.findCusByPhone(phone);
//            ordersService.orderRepository.save(new Order(ord_id, userId, null, null, customer.getCustomer_id()));
//        }
//
//        orderDetailsService.orderDetailsRepository.save(new OrderDetail(odt_id, orderDetailsService.orderDetailsRepository.maxOrderIdInODT(userId), pro_id, quantity));
//
//        return "redirect:/1";
//    }
//
//    @PostMapping("/add/customer")
//    public void addCustomerInHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String id = customerService.AUTO_CUS_ID();
//        String fullname = req.getParameter("fullname");
//        String email = req.getParameter("email");
//        String address = req.getParameter("address");
//        String phone = req.getParameter("phone-add");
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        customerService.customerRepository.save(new Customer(id, fullname, address, phone, email, timestamp));
//
//        resp.sendRedirect("/");
//
//    }
//
////    '/calculate_money/'+number
//
//    @GetMapping("/find/{phone_number}")
//    public String findCusByPhone(@PathVariable String phone_number, Model model){
////        System.out.println("hiiii");
////        System.out.println(phone_number);
//        Customer customer = customerService.findCusByPhone(phone_number);
//        model.addAttribute("customer", customer);
//        return "/home/find_cus_by_phone";
//    }
//
//    @GetMapping("/error")
//    public String error(Model model, HttpServletRequest req){
//        model.addAttribute("code", req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
//        model.addAttribute("url", req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
//        model.addAttribute("msg", req.getAttribute(RequestDispatcher.ERROR_MESSAGE));
//
//        return "/error/error";
//    }
//
//    @GetMapping("/verified")
//    public String verified(){
//
//        return "/log/verified";
//    }
//}


package com.java.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.java.models.*;
import com.java.service.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Security;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class SiteController implements ErrorController {

    @Autowired
    public ProductService productService;
    @Autowired
    public OrdersService ordersService;
    @Autowired
    public CustomerService customerService;
    @Autowired
    public PaymentService paymentService;

    @Autowired
    public OrderDetailsService orderDetailsService;
    @GetMapping("")
    public String index(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        model.addAttribute("content", "home");

        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("temp_pass", false);
        session.setAttribute("password", null);
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println(authorities.toString());
        if (myUserDetail.getCombinedUser().getAccount().isTemp_pass()) {
//            myUserDetail.getCombinedUser().getAccount().setStatus(false);
            session.setAttribute("temp_pass", myUserDetail.getCombinedUser().getAccount().isTemp_pass());
            return "redirect:/log/change_pass";
        }
        return "redirect:/1";
    }

    @GetMapping("/{pageNo}")
    public String homeProductPagination(@PathVariable int pageNo,
                                        @RequestParam(defaultValue = "9") int pageSize, Model model){
        model.addAttribute("content", "home");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());

        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);

        List<Object[]> orderListCus = ordersService.getOrderOfCustomerInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
        Optional<Object[]> totalBill = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());

        if (totalBill.isPresent()){
            model.addAttribute("totalBill", totalBill);
        }else{
            model.addAttribute("totalBill", "0");
        }

        model.addAttribute("productList", productList);
        model.addAttribute("orderListCus", orderListCus);

        return "index";

    }

    @GetMapping("/pay/invoice")
    public String orderPayment(Model model){
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> orderListCus = ordersService.getOrderOfCustomerInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
        Optional<Object[]> totalBill = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());

        if (totalBill.isPresent()){
            model.addAttribute("totalBill", totalBill);
        }else{
            model.addAttribute("totalBill", "0");
        }
        model.addAttribute("orderListCus", orderListCus);

        return "/payment/invoice";
    }

    @PostMapping("/remove/product")
    public void removeProductInHome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String odt_id = req.getParameter("productID");
        orderDetailsService.orderDetailsRepository.deleteById(odt_id);
        resp.sendRedirect("/1");
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
    public String addProductToOrderList(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();

        String pro_id = req.getParameter("product_id");
        System.out.println(pro_id);
        int quantity = Integer.parseInt(req.getParameter("quantity-ord"));
        String phone = req.getParameter("phone_number_quan");
        System.out.println(phone);
        if (phone == null || phone.isEmpty()) {
            model.addAttribute("no_input_phone", "Please enter customer phone number first");
            return "redirect:/1";
        }

        String userId = myUserDetail.getCombinedUser().getUser().getUser_id();

        String odt_id = orderDetailsService.AUTO_ODT_ID();

//        String max_ord_id = ordersService.orderRepository.maxID();
//        Optional<Order> orderOptional = ordersService.orderRepository.checkUserIdIsNull(max_ord_id);

//        String user_id_max_id = ordersService.orderRepository.findById(max_ord_id).get().getUser_id();

        Optional<String> userOptional = ordersService.orderRepository.checkUserIsOrder(userId);
        if (!userOptional.isPresent()){
            String ord_id = ordersService.AUTO_ORD_ID();
            Optional<Customer> checkCusByPhone = customerService.customerRepository.CheckCusByPhone(phone);
            Customer customer = customerService.findCusByPhone(phone);
            if (checkCusByPhone.isPresent()){
                System.out.println("save OL");
                ordersService.orderRepository.save(new Order(ord_id, userId, null, null, customer.getCustomer_id()));
            }else{
                model.addAttribute("no_input_phone", "Please enter customer phone number first");
                return "redirect:/1";
            }
        }
        orderDetailsService.orderDetailsRepository.save(new OrderDetail(odt_id, orderDetailsService.orderDetailsRepository.maxOrderIdInODT(userId), pro_id, quantity));

        return "redirect:/1";
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

    @GetMapping("/calculate/{money_Given}")
    public String calculateCustomerGivenChange(@PathVariable String money_Given,Model model, HttpServletRequest req){

//        System.out.println("hiiii");
//        String moneyGiven = req.getParameter("number");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Object[]> totalBillList = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
        if (totalBillList.isPresent()){
            Object[] totalBill = totalBillList.get();
            float row = Float.parseFloat(totalBill[0].toString());

            float cus_given_change = (float) (Math.round((row - Float.parseFloat(money_Given)) * 100.0) / 100.0);
            model.addAttribute("cus_given_change", cus_given_change);

            return "home/cus_given_change";
        }
        model.addAttribute("cus_given_change", "0.0");
        return "home/cus_given_change";
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

    @PostMapping("/home/order")
    public ResponseEntity<byte[]> downloadInvoice(HttpServletRequest req) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
            String cus_phone = req.getParameter("phone");
            String customer_given = req.getParameter("customer_given");
            String total_amount = req.getParameter("total_amount");
            List<Object[]> orderListCus = ordersService.getOrderOfCustomerInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
//            Optional<Object[]> totalBill = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());


            ByteArrayOutputStream baos = paymentService.createInvoicePdf();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "invoice.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}