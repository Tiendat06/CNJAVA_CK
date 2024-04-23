package com.java.models;

import com.java.service.customer.CustomerService;
import org.springframework.http.ResponseEntity;

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

    public VatReport prepareVATInformation() {
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

    public abstract ResponseEntity<byte[]> export(VatReport vatReport);

    public abstract void loggingAction(String orderID);
}

