package com.java.service.voucher;

import com.java.models.Voucher;
import com.java.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }
}
