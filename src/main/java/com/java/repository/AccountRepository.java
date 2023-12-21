package com.java.repository;

import com.java.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select max(account_id) from account ")
    String maxID();
    @Query("SELECT a.status, r.role_name, u.first_name, u.last_name, u.image, a.account_id, a.verify " +
            "FROM account a JOIN role r ON a.role_id = r.role_id JOIN user u ON a.account_id = u.account_id")
    Page<Object[]> getRoleAndAccId(Pageable pageable);
    List<Account> findAll();

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


