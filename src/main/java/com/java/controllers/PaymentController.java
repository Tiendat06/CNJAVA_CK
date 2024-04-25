package com.java.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.java.models.*;
import com.java.service.customer.CustomerService;
import com.java.service.order.OrderFacade;
import com.java.service.payment.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String index(Model model) {
        model.addAttribute("content", "report");
        return "redirect:/payment/report/1";
    }

    @GetMapping("/report/{pageNo}")
    public String order_list_pagination(Model model, @PathVariable int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize) {
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
                                             @RequestParam(defaultValue = "10") int pageSize) {

        Date currentDate = new Date(System.currentTimeMillis());
//        System.out.println(currentDate);

        Page<Object[]> orderList = orderFacade.getAllOrdersList(pageNo - 1, pageSize, currentDate);
        model.addAttribute("orderList", orderList);

        return "/payment/report_list";
    }

    @GetMapping("/report/order-list/{ordId}")
    public String getOderListInDetails(@PathVariable String ordId, Model model) {
        List<Object[]> odtList = ordersService.getAllOrderListDetails(ordId);
        model.addAttribute("odtList", odtList);
        return "/payment/order_list_details";
    }

    @GetMapping("/report/sort-order-list/{dateStart}/{dateEnd}")
    public String getOderListOrderByDate(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
                                         @PathVariable Date dateStart, @PathVariable Date dateEnd, Model model) {
        Page<Object[]> ordList = ordersService.getAllOrderListOrderByDate(pageNo - 1, pageSize, dateStart, dateEnd);
        model.addAttribute("orderList", ordList);
        return "/payment/order_list_sort_by_date";
    }

    @GetMapping("/report/total/{dateStart}/{dateEnd}")
    public String getTotalOrderByDate(@PathVariable Date dateStart, @PathVariable Date dateEnd, Model model) {
        List<Object[]> totalMoney = ordersService.getTotalMoneyOrderByDate(dateStart, dateEnd);
        List<Object[]> totalOrder = ordersService.getTotalOrderOrderByDate(dateStart, dateEnd);
        List<Object[]> totalProduct = ordersService.getTotalProductOrderByDate(dateStart, dateEnd);

        Float totalAmount = orderFacade.getTotalPaymentAmount(dateStart, dateEnd);

        List<Object[]> quanNPriceList = ordersService.getQuanAndPrice(dateStart, dateEnd);
        float sum = 0.0f;
        for (Object[] item : quanNPriceList) {
            int quan = (int) item[0];
            float price = (float) item[1];
            sum += quan * price;
        }
        System.out.println(totalAmount);
        System.out.println(sum);
        Float totalProfit = (float) ((float) Math.round((totalAmount - sum) * 100.0) / 100.0);

        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("totalOrder", totalOrder);
        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalProfit", totalProfit);

        return "/payment/total";
    }


    @GetMapping("/report/VAT")
    public ResponseEntity<byte[]> exportFileVAT(HttpServletRequest req, HttpServletResponse resp) throws JAXBException, IOException {
        String vatType = req.getParameter("vat-type");
        String orderID = req.getParameter("order-id-vat");
        ReportExporter exporter;
        if ("PDF".equals(vatType)) {
            exporter = new PDFReportExporter(orderID,ordersService);
        }
        else  {
            exporter = new XMLReportExporter(orderID,ordersService);
        }
        return  exporter.exportFileVAT();
    }

//    @GetMapping("/report/VAT")
//    public ResponseEntity<byte[]> exportFileVAT(HttpServletRequest req, HttpServletResponse resp) throws JAXBException, IOException {
//        String vatType = req.getParameter("vat-type");
//        String orderID = req.getParameter("order-id-vat");
//        VatReport vatReport = prepareVAT_information(orderID);
//        loggingAction(orderID);
//        if (vatType.equals("PDF")) {
//            return exportPDF(vatReport);
//        }
//        return exportXML(vatReport);
//    }
//
//    private  void loggingAction(String orderID) {
//        return;
//    }
//    private VatReport prepareVAT_information(String orderID) {
//        List<Object[]> odtList = ordersService.getAllOrderListDetails(orderID);
//        Object[] odtDetail = (Object[]) ordersService.getOrderByOrderID(orderID);
//        Timestamp timestamp = (Timestamp) odtDetail[1];
//        String cusName = (String) odtDetail[2];
//        String posFirstName = (String) odtDetail[3];
//        String posLastName = (String) odtDetail[4];
//        String status = (String) odtDetail[5];
//        float totalAmount = (float) odtDetail[6];
//
//        List<VatReportItem> items = new ArrayList<>();
//        for (Object[] data : odtList) {
//            VatReportItem item = new VatReportItem();
//            item.setProductName((String) data[0]);
//            item.setQuantity((int) data[2]);
//            item.setChangeGiven(Double.parseDouble(String.valueOf(data[3])));
//            item.setDescription((String) data[4]);
//            items.add(item);
//        }
//
//        VatReport vatReport = new VatReport();
//        vatReport.setTimestamp(timestamp);
//        vatReport.setCusName(cusName);
//        vatReport.setPosFirstName(posFirstName);
//        vatReport.setPosLastName(posLastName);
//        vatReport.setStatus(status);
//
//        vatReport.setItems(items);
//        vatReport.setTotalAmount(totalAmount);
//        return vatReport;
//    }
//
//    private ResponseEntity<byte[]> exportXML(VatReport vatReport) throws JAXBException {
//
//        // Use JAXB to convert VAT report object to XML string
//        JAXBContext jaxbContext = JAXBContext.newInstance(VatReport.class);
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatted output for readability
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        marshaller.marshal(vatReport, outputStream);
//        byte[] xmlBytes = outputStream.toByteArray();
//
//        // Set response headers for XML content
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_XML);
//        headers.setContentDisposition(ContentDisposition.attachment()
//                .filename("vat_report_"+ vatReport.getTimestamp()+ ".xml")
//                .build());
//
//        // Return XML response entity with appropriate headers
//        return new ResponseEntity<>(xmlBytes, headers, HttpStatus.OK);
//    }
//
//    public ResponseEntity<byte[]> exportPDF(VatReport vatReport) throws IOException {
//        try {
//
//        ByteArrayOutputStream baos = createInvoicePdf(vatReport);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("inline", "vat_report_" + vatReport.getTimestamp() +".pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//        // Download invoice PDF
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(baos.toByteArray());
//
//        } catch (NumberFormatException e){
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//    public ByteArrayOutputStream createInvoicePdf(VatReport vatReport) throws DocumentException {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter.getInstance(document, baos);
//        document.open();
//
//        Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.BLACK);
//        Paragraph title = new Paragraph("VAT REPORT", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//
//        Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, BaseColor.BLACK);
//
//        document.add(new Chunk("Customer name: " + vatReport.getCusName() + "\n", contentFont));
//        document.add(new Chunk("Date created: " + vatReport.getTimestamp().toString() + "\n", contentFont));
//        document.add(new Chunk("Sealer: " + vatReport.getPosFirstName() + ' ' + vatReport.getPosLastName() + "\n", contentFont));
//        document.add(new Chunk("Status: " + vatReport.getStatus() + "\n\n", contentFont));
//
//        Paragraph paragraph = new Paragraph();
//        paragraph.add(new Chunk("SN          ", contentFont));
//        paragraph.add(new Chunk("Product name", contentFont));
//        paragraph.add(new Chunk(new VerticalPositionMark()));
//        paragraph.add(new Chunk("Quantity     ", contentFont));
//        document.add(paragraph);
//
//        int i = 1;
//        for (VatReportItem item: vatReport.getItems()) {
//            document.add(createDetailParagraph(i+"           ", item.getProductName(), ""+item.getQuantity(), contentFont));
//            i++;
//        }
//        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
//        document.add(new Paragraph("Total Amount: " + String.format("%.2f", vatReport.getTotalAmount()) + "$"));
//        document.add(new Paragraph("Customer Given: " + String.format("%.2f", vatReport.getTotalAmount()) + "$"));
//        document.close();
//        return baos;
//    }
//    private Paragraph createDetailParagraph(String index, String productName, String quantity, Font contentFont) {
//        Paragraph paragraph = new Paragraph();
//        paragraph.add(new Chunk(index+" "+productName, contentFont));
//        paragraph.add(new Chunk(new VerticalPositionMark()));
//        paragraph.add(new Chunk(quantity, contentFont));
//        return paragraph;
//    }

}
