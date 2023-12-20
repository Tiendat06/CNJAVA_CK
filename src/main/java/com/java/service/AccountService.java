package com.java.service;

import com.java.models.Account;
import com.java.repository.AccountRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
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
