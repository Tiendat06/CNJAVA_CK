package com.java.controllers;

import com.java.models.MyUserDetail;
import com.java.service.OrdersService;
import com.java.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    public OrdersService ordersService;
    @Autowired
    public PaymentService paymentService;

    @GetMapping("/report")
    public String index(Model model){
        model.addAttribute("content", "report");
        return "redirect:/payment/report/1";
    }

    @GetMapping("/report/{pageNo}")
    public String order_list_pagination(Model model, @PathVariable int pageNo,
                        @RequestParam(defaultValue = "10") int pageSize){
        model.addAttribute("content", "report");
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println(currentDate);

        Page<Object[]> orderList = ordersService.getAllOrdersList(pageNo - 1, pageSize, currentDate);
        model.addAttribute("orderList", orderList);

        return "index";
    }

    @GetMapping("/report/order-list/{ordId}")
    public String getOderListInDetails(@PathVariable String ordId, Model model){
        List<Object[]> odtList = ordersService.getAllOrderListDetails(ordId);
        model.addAttribute("odtList", odtList);
        return "/payment/order_list_details";
    }

    @GetMapping("/report/sort-order-list/{dateStart}/{dateEnd}")
    public String getOderListOrderByDate(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
                                         @PathVariable Date dateStart, @PathVariable Date dateEnd, Model model){
        Page<Object[]> ordList = ordersService.getAllOrderListOrderByDate(pageNo-1, pageSize, dateStart, dateEnd);
        model.addAttribute("orderList", ordList);
        return "/payment/order_list_sort_by_date";
    }

    @GetMapping("/report/total/{dateStart}/{dateEnd}")
    public String getTotalOrderByDate(@PathVariable Date dateStart, @PathVariable Date dateEnd, Model model){
        List<Object[]> totalMoney = ordersService.getTotalMoneyOrderByDate(dateStart, dateEnd);
        List<Object[]> totalOrder = ordersService.getTotalOrderOrderByDate(dateStart, dateEnd);
        List<Object[]> totalProduct = ordersService.getTotalProductOrderByDate(dateStart, dateEnd);

        Float totalAmount = paymentService.getTotalPaymentAmount();

        List<Object[]> quanNPriceList = ordersService.getQuanAndPrice(dateStart, dateEnd);
        float sum = 0.0f;
        for (Object[] item: quanNPriceList) {
            int quan = (int) item[0];
            float price = (float) item[1];
            sum += quan * price;
        }
        System.out.println(sum);
        System.out.println(totalAmount);
//        System.out.println(sum);
//        (Math.round(sum * 100.0) / 100.0
        Float totalProfit = (float) ((float) Math.round((totalAmount - sum) *100.0) / 100.0);

        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalOrder", totalOrder);
        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalProfit", totalProfit);

        return "/payment/total";
    }

}
