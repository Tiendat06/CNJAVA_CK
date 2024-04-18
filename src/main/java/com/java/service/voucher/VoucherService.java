package com.java.service.voucher;

import com.java.models.Voucher;
import com.java.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    public Optional<Voucher> findById(Integer voucher_id){
        return voucherRepository.findById(voucher_id);
    }

    public String findVoucherByVoucherId(int id){
        return voucherRepository.findVoucherNameByVoucherId(id);
    }
}
