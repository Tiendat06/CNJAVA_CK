package com.java.models;

import ch.qos.logback.classic.Logger;
import com.java.service.customer.CustomerService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;

import java.io.ByteArrayOutputStream;


public class XMLReportExporter extends ReportExporter {

    public XMLReportExporter(String orderID, CustomerService.OrdersService ordersService) {
        super(orderID, ordersService);
    }

    @Override
    public ResponseEntity<byte[]> export(VatReport vatReport) {
        // Use JAXB to convert VAT report object to XML string
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(VatReport.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatted output for readability

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(vatReport, outputStream);
            byte[] xmlBytes = outputStream.toByteArray();

            // Set response headers for XML content
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("vat_report_" + vatReport.getTimestamp() + ".xml")
                    .build());
            return new ResponseEntity<>(xmlBytes, headers, HttpStatus.OK);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void loggingAction(String orderID) {
        final Logger logger = (Logger) LoggerFactory.getLogger(XMLReportExporter.class);
        logger.info("Logging orderID: {}", orderID);
    }
}
