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
}
