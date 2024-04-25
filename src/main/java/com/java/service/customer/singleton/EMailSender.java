package com.java.service.customer.singleton;

import com.java.models.Customer;
import com.java.models.User;
import com.java.repository.AccountRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMailSender {
//    @Autowired
//    public JavaMailSender mailSender;

    private EMailSender(){}

    private volatile static EMailSender uniqueInstance;

    public static EMailSender getInstance(){
        if(uniqueInstance == null){
            synchronized (EMailSender.class){
                if (uniqueInstance == null){
                    uniqueInstance = new EMailSender();
                }
            }
        }
        return uniqueInstance;
    }

    public synchronized void sendMail(String from, String to, String subject, List<Object[]> orderListCus, Customer customer, JavaMailSender mailSender, String voucher_name, String total_amount){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Final Project");
            helper.setTo(to);
            helper.setSubject(subject);
            String content = "Dear [[name]],<br>" + "Please check your invoice:<br>";

            content = content.replace("[[name]]", customer.getCustomer_name() /*+ " " + user.getLast_name()*/);
            for (Object[] olc: orderListCus){
                 content += "<p style='margin-bottom: 0;'>"+ olc[0].toString()+" x "+olc[2].toString()+": "+olc[3].toString()+"$" +"</p>";
            }

            if (!voucher_name.isEmpty()){
                content += "<p style='margin-bottom: 0;'>"+ voucher_name +"</p>";
            }

            content += "Total bill: " + total_amount + "$<br>Thank you,<br>" + "Final Project with love <3";

            helper.setText(content, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
