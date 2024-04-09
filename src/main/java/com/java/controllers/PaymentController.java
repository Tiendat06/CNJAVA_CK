package com.java.controllers;

import com.java.models.MyUserDetail;
import com.java.models.VatReport;
import com.java.models.VatReportItem;
import com.java.service.customer.CustomerService;
import com.java.service.order.OrderFacade;
import com.java.service.payment.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    public CustomerService.OrdersService ordersService;
    @Autowired
    public PaymentService paymentService;
    @Autowired
    public OrderFacade orderFacade;

    @GetMapping("/report")
    public String index(Model model){
        model.addAttribute("content", "report");
        return "redirect:/payment/report/1";
    }

    @GetMapping("/report/{pageNo}")
    public String order_list_pagination(Model model, @PathVariable int pageNo,
                        @RequestParam(defaultValue = "10") int pageSize){
        model.addAttribute("content", "report");
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println(currentDate);

        Page<Object[]> orderList = orderFacade.getAllOrdersList(pageNo - 1, pageSize, currentDate);
        model.addAttribute("orderList", orderList);

        return "index";
    }

    @GetMapping("/report/{pageNo}/ajax")
    public String order_list_pagination_AJAX(Model model, @PathVariable int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize){

        Date currentDate = new Date(System.currentTimeMillis());
//        System.out.println(currentDate);

        Page<Object[]> orderList = orderFacade.getAllOrdersList(pageNo - 1, pageSize, currentDate);
        model.addAttribute("orderList", orderList);

        return "/payment/report_list";
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

        Float totalAmount = orderFacade.getTotalPaymentAmount(dateStart, dateEnd);

        List<Object[]> quanNPriceList = ordersService.getQuanAndPrice(dateStart, dateEnd);
        float sum = 0.0f;
        for (Object[] item: quanNPriceList) {
            int quan = (int) item[0];
            float price = (float) item[1];
            sum += quan * price;
        }
        System.out.println(totalAmount);
        System.out.println(sum);
//        System.out.println(sum);
//        (Math.round(sum * 100.0) / 100.0
        Float totalProfit = (float) ((float) Math.round((totalAmount - sum) *100.0) / 100.0);

        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalOrder", totalOrder);
        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalProfit", totalProfit);

        return "/payment/total";
    }

//    @GetMapping("/report/VAT")
//    @ResponseBody
//    public String exportFileVAT(HttpServletRequest req, HttpServletResponse resp){
//        String vatType = req.getParameter("vat-type");
//        String orderID = req.getParameter("order-id-vat");
//        List<Object[]> odtList = ordersService.getAllOrderListDetails(orderID);
//        for (Object[] x: odtList) {
//            String productName = (String) x[0];
//            int quanity = (int) x[2];
//            double change_give = (float) x[3];
//            String description = (String) x[4];
//        }
//
//
//        return  vatType + " " + orderID;
//    }
@GetMapping("/report/VAT")
public ResponseEntity<String> exportFileVAT(HttpServletRequest req, HttpServletResponse resp) throws JAXBException {
    String vatType = req.getParameter("vat-type");
    String orderID = req.getParameter("order-id-vat");
    List<Object[]> odtList = ordersService.getAllOrderListDetails(orderID);
    return exportXML(orderID,odtList);
}

private ResponseEntity<String> exportXML(String orderID,List<Object[]> odtList) throws JAXBException {
    VatReport vatReport = new VatReport();
    vatReport.setOrderId(orderID);
    List<VatReportItem> items = new ArrayList<>();
    for (Object[] data : odtList) {
        VatReportItem item = new VatReportItem();
        item.setProductName((String) data[0]);
        item.setQuantity((int) data[2]);
        // Assuming data[3] is a monetary value, convert to double
        item.setChangeGiven(Double.parseDouble(String.valueOf(data[3])));
        item.setDescription((String) data[4]);
        items.add(item);
    }
    vatReport.setItems(items);


    double totalAmount = vatReport.getItems().stream().mapToDouble(VatReportItem::getChangeGiven).sum();
    vatReport.setTotalAmount(totalAmount);



    // Use JAXB to convert VAT report object to XML string
    JAXBContext jaxbContext = JAXBContext.newInstance(VatReport.class);
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatted output for readability

    StringWriter stringWriter = new StringWriter();
    marshaller.marshal(vatReport, stringWriter);
    String xmlString = stringWriter.toString();

    // Set response headers for XML content
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);
    headers.setContentDisposition(ContentDisposition.attachment()
            .filename("vat_report_" + orderID + ".xml")
            .build());

    // Return XML response entity with appropriate headers
    return new ResponseEntity<>(xmlString, headers, HttpStatus.OK);
}


}
