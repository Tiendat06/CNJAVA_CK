package com.java.repository;

import com.java.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select max(account_id) from account ")
    String maxID();
//    @Query("select r.role_name, a.account_id, a.status from account a join role r join user u where a.role_id = r.role_id and a.account_id = u.account_id")
//    List<Object[]> getRoleAndAccId();
    List<Account> findAll();
}
