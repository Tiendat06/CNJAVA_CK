package com.java.repository;

import com.java.models.CustomerVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher, String> {

    @Query("select max(customer_voucher_id) from customers_voucher")
    String maxID();

    @Query("select sum(CAST(v.voucher_discount as BIGDECIMAL ) ) from customers_voucher csv, voucher v " +
            "WHERE csv.voucher_id = v.voucher_id AND csv.customer_id = :cus_id")
    String totalCustomerVoucherUsed(@Param("cus_id") String cus_id);

}
