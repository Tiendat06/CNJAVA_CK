package com.java.service.user.command;

import com.java.models.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class VerifyAccountMailSenderCommand implements ICommand{

    private final JavaMailSender javaMailSender;
    private final User user;
    private final String url;
    private final String verifyCode;

    public VerifyAccountMailSenderCommand(JavaMailSender mailSender, User user, String url, String verifyCode){
        this.javaMailSender = mailSender;
        this.user = user;
        this.url = url;
        this.verifyCode = verifyCode;
    }

    @Override
    public void execute() {
        String from = "phanluonghuy4623@gmail.com";
        String to = this.user.getEmail();
        String subject = "Account Verification";
        String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Final Project with love <3";

        try {

            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Final Project");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFirst_name() + " " + user.getLast_name());
            String siteUrl = this.url + "/log/verify?code=" + this.verifyCode;

            System.out.println(siteUrl);

            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
