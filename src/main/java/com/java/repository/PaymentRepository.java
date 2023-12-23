package com.java.repository;

import com.java.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("select ROUND (sum(pay.total_amount), 2) from payment pay")
    Float totalAmount();
}
