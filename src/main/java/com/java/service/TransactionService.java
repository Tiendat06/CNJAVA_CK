package com.java.service;

import com.java.repository.TransactionRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    public TransactionRepositriy transactionRepositriy;

    public String AUTO_TRA_ID(){
        String maxID = transactionRepositriy.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("TRA%07d", number);
        }
        return "TRA0000001";
    }
}
