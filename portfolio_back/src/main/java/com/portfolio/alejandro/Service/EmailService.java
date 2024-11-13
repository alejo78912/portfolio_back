package com.portfolio.alejandro.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(String to, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("av739006@gmail.com"); 
        message.setTo(to);  
        message.setSubject(subject);  
        message.setText(messageBody); 

        mailSender.send(message);
    }
}
