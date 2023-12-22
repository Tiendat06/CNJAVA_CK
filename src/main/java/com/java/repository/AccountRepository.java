package com.java.repository;

import com.java.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select max(account_id) from account ")
    String maxID();
    @Query("SELECT a.status, r.role_name, u.first_name, u.last_name, u.image, a.account_id, a.verify " +
            "FROM account a JOIN role r ON a.role_id = r.role_id JOIN user u ON a.account_id = u.account_id")
    Page<Object[]> getRoleAndAccId(Pageable pageable);
    List<Account> findAll();

    @Query("SELECT a.verify_code FROM account a JOIN user u ON a.account_id = u.account_id WHERE u.user_id = :user_id")
    String findVerifyCodeByUserId(@Param("user_id") String user_id);

    @Query("SELECT a.verify_code FROM account a WHERE a.verify_code = :verify_code")
    String findVerifyAccount_id(@Param("verify_code") String verify_code);


    @Query("SELECT a FROM account a WHERE a.verify_code = :verify_code")
    Account findByVerifyCode(@Param("verify_code") String verify_code);

    @Modifying
    @Transactional
    @Query("UPDATE account a SET a.verify = true, a.verify_code = null WHERE a.account_id = :account_id")
    void updateStatusAndVerifyCode(@Param("account_id") String account_id);

    @Modifying
    @Transactional
    @Query("UPDATE account a SET a.verify_code = :verify_code WHERE a.account_id = :account_id")
    void updateVerifyCode(@Param("account_id") String account_id, @Param("verify_code") String verify_code);


//    @Query("SELECT u.email FROM user u JOIN account a ON a.account_id = u.account_id WHERE a.account_id = :account_id")
//    String findEmailByAccount_id(@Param("user_id") String account_id);
    @Query("SELECT u.email FROM user u JOIN account a ON a.account_id = u.account_id WHERE a.account_id = :account_id")
    String findEmailByAccount_id(@Param("account_id") String account_id);



    default void updateRoleByAccID(String id, int roleId){
        findById(id).ifPresent(a -> {
            a.setRole_id(roleId);
            a.setStatus(true);
            save(a);
        });
    }

    default void updateStatusByAccID(String id, boolean status){
        findById(id).ifPresent(a -> {
            a.setStatus(status);
            save(a);
        });
    }

    @Query("SELECT a FROM account a WHERE a.account_id = :accountId")
    Account findByAccountId(@Param("accountId") String accountId);

}


