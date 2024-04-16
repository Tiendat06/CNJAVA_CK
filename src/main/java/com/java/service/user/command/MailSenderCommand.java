package com.java.service.user.command;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;


public class MailSenderCommand implements ICommand{

    private final JavaMailSender javaMailSender;
    private final MimeMessage message;

    public MailSenderCommand(JavaMailSender mailSender, MimeMessage msg){
        this.javaMailSender = mailSender;
        this.message = msg;
    }

    @Override
    public void execute() {
        javaMailSender.send(this.message);
    }
}
