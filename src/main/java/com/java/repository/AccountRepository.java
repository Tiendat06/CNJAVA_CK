package com.java.repository;

import com.java.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("SELECT a FROM account a WHERE a.account_id = :accountId")
    Account findByAccountId(@Param("accountId") String accountId);
}

