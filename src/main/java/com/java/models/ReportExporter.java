package com.java.models;

import com.itextpdf.text.DocumentException;
import com.java.service.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class ReportExporter {

    private final String orderID;

    private final CustomerService.OrdersService ordersService;

    public ReportExporter(String orderID, CustomerService.OrdersService ordersService) {
        this.orderID = orderID;
        this.ordersService = ordersService;
    }

    public ResponseEntity<byte[]> exportFileVAT() {
        VatReport vatReport = prepareVATInformation();
        loggingAction(orderID);
        return export(vatReport);
    }

    protected VatReport prepareVATInformation() {
        List<Object[]> odtList = ordersService.getAllOrderListDetails(orderID);
        Object[] odtDetail = (Object[]) ordersService.getOrderByOrderID(orderID);
        Timestamp timestamp = (Timestamp) odtDetail[1];
        String cusName = (String) odtDetail[2];
        String posFirstName = (String) odtDetail[3];
        String posLastName = (String) odtDetail[4];
        String status = (String) odtDetail[5];
        float totalAmount = (float) odtDetail[6];

        List<VatReportItem> items = new ArrayList<>();
        for (Object[] data : odtList) {
            VatReportItem item = new VatReportItem();
            item.setProductName((String) data[0]);
            item.setQuantity((int) data[2]);
            item.setChangeGiven(Double.parseDouble(String.valueOf(data[3])));
            item.setDescription((String) data[4]);
            items.add(item);
        }

        VatReport vatReport = new VatReport();
        vatReport.setTimestamp(timestamp);
        vatReport.setCusName(cusName);
        vatReport.setPosFirstName(posFirstName);
        vatReport.setPosLastName(posLastName);
        vatReport.setStatus(status);

        vatReport.setItems(items);
        vatReport.setTotalAmount(totalAmount);
        return vatReport;
    }

    protected abstract ResponseEntity<byte[]> export(VatReport vatReport);

    protected abstract void loggingAction(String orderID);
}

