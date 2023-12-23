package com.java.service;

import com.java.models.Account;
import com.java.models.User;
import com.java.repository.AccountRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class AccountService {
    @Autowired
    public AccountRepository accountRepository;

//    public List<Object[]> getRoleAndAccId(){
//        return accountRepository.getRoleAndAccId();
//    }

    public Page<Object[]> getAccPagination(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return accountRepository.getRoleAndAccId(pageable);
    }

    public void updateStatusByAccId(String accId, boolean status){
        accountRepository.updateStatusByAccID(accId, status);
    }

    public Account getAccountByEmailAndPassword(){

        return new Account();
    }

    public void updateRoleByAccId(String id, int roleId){
        accountRepository.updateRoleByAccID(id, roleId);
    }

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public void deleteByID(String id){
        accountRepository.deleteById(id);
    }

    public String AUTO_ACC_ID(){
        String maxID = accountRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("ACC%07d", number);
        }
        return "ACC0000001";
    }
}
