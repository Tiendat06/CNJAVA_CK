package com.java.repository;

import com.java.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("select ROUND (sum(pay.total_amount), 2) from payment pay where pay.date_created >= :dateStart and pay.date_created <= :dateEnd")
    Float totalAmount(@Param("dateStart") Date dateStart, Date dateEnd);

    @Query("select max (payment_id) from payment ")
    String maxID();
}
