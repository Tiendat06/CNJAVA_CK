package com.java.repository;

import com.java.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositriy extends JpaRepository<Transaction, String> {

    @Query("select max (transaction_id) from transaction ")
    String maxID();
}
