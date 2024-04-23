package com.java.repository;

import com.java.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, String> {

    @Query("select count(odt.order_id) from order_details odt where odt.order_id = :ord_Id")
    String checkOrdIdInOdtAfterDelOdtId(@Param("ord_Id")String ord_Id);

//    @Query("delete from orders ord where ord.order_id ")

    @Query("select max (order_details_id) from order_details ")
    String maxID();

//    void deleteOrderDetailByOrder_id(String ord_id);

    @Query("select max(ord.order_id) from orders ord WHERE " +
            "ord.user_id = :userId and ord.date_created is null ")
    String maxOrderIdInODT(@Param("userId") String userId);

    @Query("select od from order_details od where od.product_id = :id")
    List<OrderDetail> checkProduct(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("delete from order_details odt where odt.order_id = :ord_id")
    void deleteAllProductByOrderId(@Param("ord_id") String ord_id);

    @Query("select cus.customer_id, " +  //5
            " pro.product_name, pro.product_img, odt.quantity, pro.description, round((odt.quantity * pro.retail_price), 2) as order_money from order_details odt " +   // 13
            ", orders ord , product pro , customers cus , transaction tra " +
            ", payment pay " +
            "where odt.order_id = ord.order_id and odt.order_id = tra.order_id and ord.order_id = tra.order_id and " +
            "odt.product_id = pro.product_id and ord.customer_id = cus.customer_id and " +
            "tra.order_id = ord.order_id and pay.date_created = ord.date_created " +
            "and tra.payment_id = pay.payment_id and cus.customer_id = :cusid and pay.date_created = :date_created")
    List<Object[]> getPurchaseHistoryDetailsByCustomerId(@Param("cusid") String id, @Param("date_created") Timestamp date_created);

    @Query("select pay.date_created, SUM(odt.quantity), round((pay.total_amount), 2), round((pay.change_given), 2), round(((pay.total_amount) - (pay.change_given)), 2) as cus_pay, cus.customer_id " +
            "from order_details odt " +
            ", orders ord , product pro , customers cus , transaction tra " +
            ", payment pay " +
            "where odt.order_id = ord.order_id and odt.order_id = tra.order_id and ord.order_id = tra.order_id and " +
            "odt.product_id = pro.product_id and ord.customer_id = cus.customer_id and " +
            "tra.order_id = ord.order_id and pay.date_created = ord.date_created " +
            "and tra.payment_id = pay.payment_id and cus.customer_id = :cusid GROUP BY pay.date_created")
    List<Object[]> getPurchaseHistoryListByCustomerId(@Param("cusid") String id);
}
