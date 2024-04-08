package com.java.service.order;

import com.itextpdf.text.DocumentException;
//import com.java.controllers.PaypalController;
import com.java.models.*;
import com.java.service.transaction.TransactionService;
import com.java.service.customer.CustomerService;
import com.java.service.payment.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OrderFacade {
    @Autowired
    private CustomerService.OrdersService ordersService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private  CustomerService customerService;
//    @Autowired
//    private PaypalController paypalController;

    public Page<Object[]> getAllOrdersList(int pageNo, int pageSize, Date date) {
        return ordersService.getAllOrdersList(pageNo,pageSize,date);
    }

    public List<OrderDetail> getAllOrderDetails(String id) {
        return orderDetailsService.getAllOrderDetails(id);
    }

    public Float getTotalPaymentAmount(Date dateStart, Date dateEnd) {
        return paymentService.getTotalPaymentAmount(dateStart, dateEnd);
    }

    public ByteArrayOutputStream createInvoicePdf(float customerGiven, float givenChange, String customerName) throws DocumentException, DocumentException {
        return paymentService.createInvoicePdf(customerGiven, givenChange, customerName);
    }

    public String generateAutoOrderId() {
        return ordersService.AUTO_ORD_ID();
    }

    public String generateAutoOrderDetailId() {
        return orderDetailsService.AUTO_ODT_ID();
    }

    public String generateAutoPaymentId() {
        return paymentService.AUTO_PAY_ID();
    }

    public ResponseEntity<byte[]> downloadInvoice(Model model, HttpServletRequest req, HttpServletResponse resp,Customer customer) throws IOException {
        String customer_given = req.getParameter("customer_given");
        String change_given = req.getParameter("total_amount");
        Float total_amount = Float.parseFloat(customer_given) - Float.parseFloat(change_given);
        String phone = req.getParameter("phone");

        HttpSession session = req.getSession();
        try {
            if (Float.parseFloat(customer_given) >= total_amount){
                ByteArrayOutputStream baos = paymentService.createInvoicePdf(Float.parseFloat(customer_given), Float.parseFloat(change_given), customer.getCustomer_name());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("inline", "invoice.pdf");
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");


                // Download invoice PDF
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(baos.toByteArray());
            } else {
                session.setAttribute("low_customer_given", true);
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("/1");
        return null;
    }
    public String saveInvoiceDetailsToDatabase(HttpServletRequest req, Float total_amount, Float change_given, Customer customer,String payment_method) {
        try {
            String userId = getUserIdFromRequest(req);
            Optional<String> order_id = ordersService.getOrderToPayment(userId);
            String tra_id = transactionService.AUTO_TRA_ID();
            String pay_id = paymentService.AUTO_PAY_ID();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            if (order_id.isPresent()){
                Optional<String> checkCusFirst = customerService.customerRepository.checkIsFirstCustomer(customer.getCustomer_id());
                if (checkCusFirst.isPresent()){
                    customer.setDate_created(timestamp);
                }
                String order_id_val = order_id.get();
                String totalCustomerOrder = ordersService.currentCustomerOrder(customer.getCustomer_id());
                ordersService.updateOrderToPayment(order_id_val, timestamp, totalCustomerOrder);
                transactionService.transactionRepositriy.save(new Transaction(tra_id, pay_id, "Completed", order_id_val));
                String payment_method_id = payment_method.equals("cash") ? "PMM0000002" : "PMM0000003";
                paymentService.paymentRepository.save(new Payment(pay_id, payment_method_id, total_amount, change_given, timestamp));
                return order_id_val;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUserIdFromRequest(HttpServletRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail myUserDetail = (MyUserDetail) auth.getPrincipal();
        return myUserDetail.getCombinedUser().getUser().getUser_id();
    }



}
