package com.java.service.user.command;

import com.java.models.Customer;
import com.java.models.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

public class ConfirmOrderMailSenderCommand implements ICommand{
    private final JavaMailSender javaMailSender;
    private final Customer customer;
    private final String voucher_name;
    private final String total_amount;
    private final List<Object[]> orderListCus;

    public ConfirmOrderMailSenderCommand(JavaMailSender mailSender, Customer customer, String voucher_name, String total_amount, List<Object[]> orderListCus){
        this.javaMailSender = mailSender;
        this.customer = customer;
        this.voucher_name = voucher_name;
        this.total_amount = total_amount;
        this.orderListCus = orderListCus;
    }

    @Override
    public void execute() {
        try {
            String from = "phanluonghuy4623@gmail.com";
            String to = this.customer.getCustomer_email();
            String subject = "Invoice Verification";

            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Final Project");
            helper.setTo(to);
            helper.setSubject(subject);
            String content = "Dear [[name]],<br>" + "Please check your invoice:<br>";

            content = content.replace("[[name]]", this.customer.getCustomer_name() /*+ " " + user.getLast_name()*/);
            for (Object[] olc: this.orderListCus){
                content += "<p style='margin-bottom: 0;'>"+ olc[0].toString()+" x "+olc[2].toString()+": "+olc[3].toString()+"$" +"</p>";
            }

            if (!this.voucher_name.isEmpty()){
                content += "<p style='margin-bottom: 0;'>"+ this.voucher_name +"</p>";
            }

            content += "Total bill: " + total_amount + "$<br>Thank you,<br>" + "Final Project with love <3";

            helper.setText(content, true);

            this.javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
