package com.java.models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.java.service.customer.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

public class PDFReportExporter extends ReportExporter {


    public PDFReportExporter(String orderID, CustomerService.OrdersService ordersService) {
        super(orderID, ordersService);
    }

    @Override
    protected ResponseEntity<byte[]> export(VatReport vatReport) {
        try {

            ByteArrayOutputStream baos = createInvoicePdf(vatReport);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "vat_report_" + vatReport.getTimestamp() + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            // Download invoice PDF
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void loggingAction(String orderID) {

    }
    private ByteArrayOutputStream createInvoicePdf(VatReport vatReport) throws DocumentException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.BLACK);
        Paragraph title = new Paragraph("VAT REPORT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, BaseColor.BLACK);

        document.add(new Chunk("Customer name: " + vatReport.getCusName() + "\n", contentFont));
        document.add(new Chunk("Date created: " + vatReport.getTimestamp().toString() + "\n", contentFont));
        document.add(new Chunk("Sealer: " + vatReport.getPosFirstName() + ' ' + vatReport.getPosLastName() + "\n", contentFont));
        document.add(new Chunk("Status: " + vatReport.getStatus() + "\n\n", contentFont));

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk("SN          ", contentFont));
        paragraph.add(new Chunk("Product name", contentFont));
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(new Chunk("Quantity     ", contentFont));
        document.add(paragraph);

        int i = 1;
        for (VatReportItem item : vatReport.getItems()) {
            document.add(createDetailParagraph(i + "           ", item.getProductName(), "" + item.getQuantity(), contentFont));
            i++;
        }
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("Total Amount: " + String.format("%.2f", vatReport.getTotalAmount()) + "$"));
        document.add(new Paragraph("Customer Given: " + String.format("%.2f", vatReport.getTotalAmount()) + "$"));
        document.close();
        return baos;
    }

    private Paragraph createDetailParagraph(String index, String productName, String quantity, Font contentFont) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(index + " " + productName, contentFont));
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(new Chunk(quantity, contentFont));
        return paragraph;
    }
}
