package com.java.repository;

import com.java.models.CustomerVoucher;
import com.java.service.customer.CustomerVoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher, String> {

    @Query("select max(customer_voucher_id) from customers_voucher")
    String maxID();

    default void updateCustomerVoucherDateUsed(CustomerVoucher customerVoucher){
        findById(customerVoucher.getCustomer_voucher_id()).ifPresent(csv -> {
            csv.setDate_used(customerVoucher.getDate_used());
            save(csv);
        });
    }

    @Query("select csv from customers_voucher csv where csv.customer_id = :cus_id and csv.voucher_id = :voucher_id and csv.date_used is null")
    Optional<CustomerVoucher> findCustomerVoucherByCusIdAndVoucherId(@Param("cus_id") String customer_id, @Param("voucher_id") int voucher_id);

    @Query("select sum(CAST(v.voucher_discount as BIGDECIMAL ) ) from customers_voucher csv, voucher v " +
            "WHERE csv.voucher_id = v.voucher_id AND csv.customer_id = :cus_id")
    String totalCustomerVoucherUsed(@Param("cus_id") String cus_id);

    @Query("SELECT csv.voucher_id FROM customers_voucher csv " +
            "where csv.customer_id = :cus_id and csv.date_used is null ")
    String getCustomerVoucherId(@Param("cus_id") String cus_id);
}
