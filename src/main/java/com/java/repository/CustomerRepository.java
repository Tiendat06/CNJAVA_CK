package com.java.repository;

import com.java.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("select max (customer_id) from customers ")
    String maxID();

    @Query("select c from customers c where c.customer_phone = :phone")
    Customer findCusByPhone(@Param("phone") String phone);
}
