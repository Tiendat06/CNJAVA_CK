package com.java.service.account.observer;

import com.java.models.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;

public class WorkingSubject extends MailSenderSubject{
//    private final int count = 100;
    protected int value;
    private final User user;
    private final JavaMailSender javaMailSender;
    private final String url;
    private final String verifyCode;
    public void setValue(int value){
        this.value = value;
        dataChanged();
    }

    public WorkingSubject(User user, JavaMailSender mailSender, String url, String verifyCode){
        this.value = 0;
        this.user = user;
        this.javaMailSender = mailSender;
        this.url = url;
        this.verifyCode = verifyCode;
    }

    public void calculateProgress(){
        int totalSteps = 10;
        for (int step = 1; step <= totalSteps; step++) {
            boolean isStepCompleted = sendMailStep(step);
            if (!isStepCompleted) {
                break;
            }
            setValue( (int) ((double) step / totalSteps * 100));
        }
    }

    public boolean sendMailStep(int step){
        String from = "";
        String to = "";
        String subject = "";
        String content = "";
        MimeMessage message = null;
        MimeMessageHelper helper = null;
        try{

            if (step == 1){
                from = "phanluonghuy4623@gmail.com";
                to = this.user.getEmail();
                subject = "Account Verification";
                content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                        + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Final Project with love <3";
                if (!to.isEmpty()){
                    return true;
                }
                return false;
            } else if(step == 2){
                    message = this.javaMailSender.createMimeMessage();
                    helper = new MimeMessageHelper(message);
                    helper.setFrom(from, "Final Project");
                    helper.setTo(to);
                    helper.setSubject(subject);
                    if (message != null){
                        return true;
                    }
                    return false;

            } else if(step == 4){
                content = content.replace("[[name]]", user.getFirst_name() + " " + user.getLast_name());
                String siteUrl = this.url + "/log/verify?code=" + this.verifyCode;

                content = content.replace("[[URL]]", siteUrl);

                helper.setText(content, true);
                return true;
            } else {
                javaMailSender.send(message);
                return true;
            }
        } catch (Exception e){

        }
        return true;
    }

    @Override
    public void dataChanged() {
        for (IMailSenderObserver ob: observers){
            ob.update(this.value);
        }
    }
}
