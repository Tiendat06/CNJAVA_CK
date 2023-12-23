package com.java.repository;

import com.java.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select ord.order_id, ord.date_created, cus.customer_name, use.first_name, use.last_name, tra.status, pay.total_amount " +
            "from orders ord, transaction tra, payment pay, user use, customers cus " +
            "where ord.order_id = tra.order_id and pay.transaction_id = tra.transaction_id and " +
            "ord.user_id = use.user_id and cus.customer_id = ord.customer_id and DATE(pay.date_created) <= :date")
    Page<Object[]> getAllOrderList(Pageable pageable, @Param("date") Date date);

    @Query("select pro.product_name, pro.product_img, odt.quantity, pay.change_given, pro.description from product pro, orders ord, order_details odt, payment pay, transaction tra " +
            "where pro.product_id = odt.product_id and ord.order_id = odt.order_id " +
            "and pay.transaction_id = tra.transaction_id and tra.order_id = ord.order_id " +
            "and pay.payment_id = tra.payment_id and ord.order_id = :ordId")
    List<Object[]> getOderListDetails(@Param("ordId") String ordId);

    @Query("select ord.order_id, ord.date_created, cus.customer_name, use.first_name, use.last_name, tra.status, pay.total_amount " +
            "from orders ord, transaction tra, payment pay, user use, customers cus " +
            "where ord.order_id = tra.order_id and pay.transaction_id = tra.transaction_id and " +
            "ord.user_id = use.user_id and cus.customer_id = ord.customer_id and DATE(pay.date_created) >= :date_start and DATE(pay.date_created) <= :date_end")
    Page<Object[]> getAllOrderListSortByDatetime(Pageable pageable, @Param("date_start") Date date_start, @Param("date_end") Date date_end);

    @Query("select ROUND(SUM(pay.total_amount), 2) from payment pay " +
            "where DATE(pay.date_created) >= :date_start and DATE(pay.date_created) <= :date_end")
    List<Object[]> totalMoneyOrderByDate(@Param("date_start") Date date_start, @Param("date_end") Date date_end);

    @Query("select count(ord.order_id) from orders ord where " +
            "DATE(ord.date_created) >= :date_start and DATE(ord.date_created) <= :date_end ")
    List<Object[]> totalOrderOrderByDate(@Param("date_start") Date date_start, @Param("date_end") Date date_end);

    @Query("select sum(odt.quantity) from order_details odt, orders ord where " +
            "DATE(ord.date_created) >= :date_start and DATE(ord.date_created) <= :date_end " +
            "and ord.order_id = odt.order_id")
    List<Object[]> totalProductOrderByDate(@Param("date_start") Date date_start, @Param("date_end") Date date_end);

    @Query("select odt.quantity, pro.product_price from order_details odt, product pro \n" +
            "where odt.product_id = pro.product_id")
    List<Object[]> getQuanAndPrice();
}
