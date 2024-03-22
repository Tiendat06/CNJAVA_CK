package com.java.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.java.models.MyUserDetail;
import com.java.models.Payment;
import com.java.models.Product;
import com.java.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    public PaymentRepository paymentRepository;
    @Autowired
    public OrdersService ordersService;

    public Float getTotalPaymentAmount(Date dateStart, Date dateEnd){
        return paymentRepository.totalAmount(dateStart, dateEnd);
    }

    public ByteArrayOutputStream createInvoicePdf(float customer_given, float given_change, String cus_name) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.BLACK);
        Paragraph title = new Paragraph("Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

//        document.add(new Paragraph("Customer name: "));
//        document.add(new Paragraph(""));

        Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, BaseColor.BLACK);

        document.add(new Chunk("Customer name: " + cus_name, contentFont));

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk("Product name", contentFont));
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(new Chunk("Quantity     ", contentFont));
        paragraph.add(new Chunk("Total price", contentFont));
        document.add(paragraph);

        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Object[]> orderListCus = ordersService.getOrderOfCustomerInHome(myUserDetail.getCombinedUser().getUser().getUser_id());
        Optional<Object[]> totalBill = ordersService.totalBillInHome(myUserDetail.getCombinedUser().getUser().getUser_id());

        int i = 1;
        for (Object[] olc: orderListCus) {
            document.add(createDetailParagraph(i+"", olc[0].toString(), "x"+olc[2].toString(), olc[3].toString()+"$", contentFont));
            i++;
        }
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

        document.add(new Paragraph("Total Amount: " + totalBill.get()[0] + "$"));
        document.add(new Paragraph("Customer Given: " + customer_given + "$"));
        document.add(new Paragraph("Change Given: "+ given_change + "$"));
        document.close();
        return baos;
    }
    private Paragraph createDetailParagraph(String index, String productName, String quantity, String price, Font contentFont) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(index+") "+productName, contentFont));
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(new Chunk(quantity+"             "+price, contentFont));
        return paragraph;
    }

    public String AUTO_PAY_ID(){
        String maxID = paymentRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("PAY%07d", number);
        }
        return "PAY0000001";
    }


}
