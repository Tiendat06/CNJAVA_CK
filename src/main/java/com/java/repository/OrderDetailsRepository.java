package com.java.repository;

import com.java.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, String> {
    @Query("select od from order_details od where od.product_id = :id")
    List<OrderDetail> checkProduct(@Param("id") String id);

    @Query("select cus.customer_id, " +  //5
            " pro.product_name, pro.product_img, odt.quantity, pro.description, round((odt.quantity * pro.retail_price), 2) as order_money from order_details odt " +   // 13
            ", orders ord , product pro , customers cus , transaction tra " +
            ", payment pay " +
            "where odt.order_id = ord.order_id and odt.order_id = tra.order_id and ord.order_id = tra.order_id and " +
            "odt.product_id = pro.product_id and ord.customer_id = cus.customer_id and " +
            "tra.order_id = ord.order_id and pay.transaction_id = pay.transaction_id " +
            "and tra.payment_id = pay.payment_id and cus.customer_id = :cusid")
    List<Object[]> getPurchaseHistoryDetailsByCustomerId(@Param("cusid") String id);

    @Query("select pay.date_created, SUM(odt.quantity), SUM(pay.total_amount), SUM(pay.change_given), (SUM(pay.total_amount) - SUM(pay.change_given)) as cus_pay, cus.customer_id " +
            "from order_details odt " +
            ", orders ord , product pro , customers cus , transaction tra " +
            ", payment pay " +
            "where odt.order_id = ord.order_id and odt.order_id = tra.order_id and ord.order_id = tra.order_id and " +
            "odt.product_id = pro.product_id and ord.customer_id = cus.customer_id and " +
            "tra.order_id = ord.order_id and pay.transaction_id = pay.transaction_id " +
            "and tra.payment_id = pay.payment_id and cus.customer_id = :cusid")
    List<Object[]> getPurchaseHistoryListByCustomerId(@Param("cusid") String id);
}
