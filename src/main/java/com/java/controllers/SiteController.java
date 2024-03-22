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

import com.java.models.*;
import com.java.service.*;
import com.java.service.proxy.ProxyCustomerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @Autowired
    public TransactionService transactionService;
    @Autowired
    public ProxyCustomerService proxyCustomerService;
    @Autowired
    public OrderFacade orderFacade;

    @GetMapping("")
    public String index(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        model.addAttribute("content", "home");

        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("temp_pass", false);
        session.setAttribute("password", null);
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

//        ADAPTER PATTERN (TTD)
        List<String> provinceAPI = customerService.getProvinceAPI();
//        provinceAPI.forEach(System.out::println);

        model.addAttribute("productList", productList);
        model.addAttribute("orderListCus", orderListCus);
        model.addAttribute("provinceAPI", provinceAPI);

        return "index";

    }

    @GetMapping("/{pageNo}/ajax")
    public String homeProductPagination_AJAX(@PathVariable int pageNo,
                                             @RequestParam(defaultValue = "9") int pageSize, Model model){

        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);

        model.addAttribute("productList", productList);

        return "home/product_list";

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
        String ord_id = req.getParameter("ordID");
        String pro_id = req.getParameter("product-id_real");
        int item_quantity = Integer.parseInt(req.getParameter("quantity-remove"));

        orderDetailsService.orderDetailsRepository.deleteById(odt_id);
        productService.productRepository.findById(pro_id).ifPresent(p -> {
            p.setQuantity_stock(p.getQuantity_stock() + item_quantity);
            productService.productRepository.save(p);
        });
        Integer count = Integer.parseInt(orderDetailsService.orderDetailsRepository.checkOrdIdInOdtAfterDelOdtId(ord_id));
        if (count == 0){
            ordersService.orderRepository.deleteById(ord_id);
        }

        resp.sendRedirect("/1");
    }

    @GetMapping("/home/category/{id}")
    public String getProductCategory(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "9") int pageSize,
                                     @PathVariable int id, Model model){
//        model.addAttribute("content", "home");
//        System.out.println("hi");
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
//        System.out.println(pro_id);
        int quantity = Integer.parseInt(req.getParameter("quantity-ord"));
        String phone = req.getParameter("phone_number_quan");
//        System.out.println(phone);
        System.out.println("quan ord: "+quantity);

        int quan_stock = Integer.parseInt(req.getParameter("quan_stock"));
        System.out.println("hello word: "+quan_stock);
        if (quantity > quan_stock){
            return "redirect:/1";
        }

        if (phone == null || phone.isEmpty()) {
            model.addAttribute("no_input_phone", "Please enter customer phone number first");
            return "redirect:/1";
        }

        String userId = myUserDetail.getCombinedUser().getUser().getUser_id();

        String odt_id = orderFacade.generateAutoOrderDetailId();

//        String max_ord_id = ordersService.orderRepository.maxID();
//        Optional<Order> orderOptional = ordersService.orderRepository.checkUserIdIsNull(max_ord_id);

//        String user_id_max_id = ordersService.orderRepository.findById(max_ord_id).get().getUser_id();

        HttpSession session = req.getSession();

        Optional<String> userOptional = ordersService.orderRepository.checkUserIsOrder(userId);
        if (!userOptional.isPresent()){
            String ord_id = orderFacade.generateAutoOrderId();
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
        productService.productRepository.findById(pro_id).ifPresent(p -> {
            p.setQuantity_stock(p.getQuantity_stock() - quantity);
            productService.productRepository.save(p);
        });
        return "redirect:/1";
    }

    @PostMapping("/add/customer")
    public void addCustomerInHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = customerService.AUTO_CUS_ID();
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone-add");
        Customer customer = new Customer(id, fullname, address, phone, email, null);

        proxyCustomerService.addCustomer(customer);
//        customerService.customerRepository.save(new Customer(id, fullname, address, phone, email, null));

        resp.sendRedirect("/");

    }

    @GetMapping("/add/customer/AJAX")
    public String addCustomerInHomePageAJAX(@RequestParam String fullname,
                                            @RequestParam String email,
                                            @RequestParam String address,
                                            @RequestParam String phone,
                                            Model model){
        String id = customerService.AUTO_CUS_ID();

//        System.out.println(fullname);
//        System.out.println(email);
//        System.out.println(address);
//        System.out.println(phone);

        Customer customer = new Customer(id, fullname, address, phone, email, null);

//        PROXY PATTERN (TTD)
        String result = proxyCustomerService.addCustomer(customer);
        if (result.equals("Success")){
            model.addAttribute("result", "Add Successfully !");
        }else{
            model.addAttribute("result", result);
        }

        return "/home/validate_add_cus";
    }

    @GetMapping("/find/{phone_number}")
    public String findCusByPhone(@PathVariable String phone_number, Model model, HttpServletRequest req){
//        System.out.println("hiiii");
//        System.out.println(phone_number);
        HttpSession session = req.getSession();

        Customer customer = customerService.findCusByPhone(phone_number);
        session.setAttribute("customer_phone", customer.getCustomer_phone());
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
            float totalMoney = Float.parseFloat(totalBill[0].toString());

            float cus_given_change = (float) (Math.round((Float.parseFloat(money_Given) - totalMoney) * 100.0) / 100.0);
            model.addAttribute("cus_given_change", cus_given_change);

            return "home/cus_given_change";
        }
        model.addAttribute("cus_given_change", "0.0");
        return "home/cus_given_change";
    }

    @GetMapping("/search-home/{character}")
    public String searchInHome(@PathVariable String character, Model model,
                               @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "9") int pageSize){
        Page<Product> search = productService.searchInHome(pageNo, pageSize, character);
        model.addAttribute("productList", search);
        return "/home/search";
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

    @PostMapping("/cancel/payment")
    public void cancelPayment(HttpServletResponse resp, HttpServletRequest req) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
        Optional<String> ord_id = ordersService.orderRepository.getOrderIdToCancel(myUserDetail.getCombinedUser().getUser().getUser_id());
        String phone = req.getParameter("phone-cancel");
        System.out.println(phone);
        HttpSession session = req.getSession();
        session.removeAttribute("customer_phone");

        if (ord_id.isPresent()){
            Customer customer = customerService.findCusByPhone(phone);
            Optional<String> checkFirstCus = customerService.customerRepository.checkIsFirstCustomer(customer.getCustomer_id());
            if (checkFirstCus.isPresent()){
                customerService.customerRepository.deleteById(customer.getCustomer_id());
            }
            String ord_id_val = ord_id.get();
            orderDetailsService.orderDetailsRepository.deleteAllProductByOrderId(ord_id_val);
            ordersService.orderRepository.deleteById(ord_id_val);
        }

        resp.sendRedirect("/1");
    }

    @PostMapping("/home/order")
//    FACADE PATTERN (PLH)
    public ResponseEntity<byte[]> downloadInvoice(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String customer_given = req.getParameter("customer_given");
        String change_given = req.getParameter("total_amount");
        Float total_amount = Float.parseFloat(customer_given) - Float.parseFloat(change_given);
//        pending
        String phone = req.getParameter("phone");
        Customer customer = customerService.findCusByPhone(phone);
        try {
            ResponseEntity<byte[]> invoice =  orderFacade.downloadInvoice(model,req,resp,customer);
            String order_id = orderFacade.saveInvoiceDetailsToDatabase(req,total_amount, Float.valueOf(change_given),customer);
            if (order_id!=null) {
                if (orderFacade.processPayment(order_id))
                    return invoice;
                else  return null;
            }
        } catch (Exception ignored) {
        }


//        System.out.println(customer_given);
//        System.out.println(total_amount);
//        HttpSession session = req.getSession();
//        try {
//            if (Float.parseFloat(customer_given) >= total_amount){
//                try {
//                    ByteArrayOutputStream baos = orderFacade.createInvoicePdf(Float.parseFloat(customer_given), Float.parseFloat(change_given), customer.getCustomer_name());
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.setContentType(MediaType.APPLICATION_PDF);
//                    headers.setContentDispositionFormData("inline", "invoice.pdf");
//                    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                    MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
//
//                    String userId = myUserDetail.getCombinedUser().getUser().getUser_id();
//
////                    Float change_given = Float.parseFloat(customer_given) - Float.parseFloat(total_amount);
//                    Optional<String> order_id = ordersService.getOrderToPayment(userId);
//                    String tra_id = transactionService.AUTO_TRA_ID();
//                    String pay_id = orderFacade.generateAutoPaymentId();
//                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//                    if (order_id.isPresent()){
//                        Optional<String> checkCusFirst = customerService.customerRepository.checkIsFirstCustomer(customer.getCustomer_id());
//                        if (checkCusFirst.isPresent()){
//                            customer.setDate_created(timestamp);
//                        }
//                        String order_id_val = order_id.get();
//                        ordersService.updateOrderToPayment(order_id_val, timestamp, "Noted");
//                        transactionService.transactionRepositriy.save(new Transaction(tra_id, pay_id, "Completed", order_id_val));
//                        paymentService.paymentRepository.save(new Payment(pay_id, "PMM0000002", total_amount, Float.parseFloat(change_given), timestamp));
//
//                    }
//
//                    return ResponseEntity.ok()
//                            .headers(headers)
//                            .body(baos.toByteArray());
//                } catch (Exception e) {
//                    return ResponseEntity.status(500).body(null);
//                }
//            }else{
//                session.setAttribute("low_customer_given", true);
//            }
//        } catch (NumberFormatException e){
//            e.printStackTrace();
//        }

        resp.sendRedirect("/1");
        return null;

    }

    @GetMapping("/site/payment_success")
    public String paymentSuccess(){

        return "/home/payment_success";
    }

    @GetMapping("/site/payment_failed")
    public String paymentFailed(){

        return "/home/payment_failed";
    }

}