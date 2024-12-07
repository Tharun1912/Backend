package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, String orderDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Order Confirmation");
        message.setText(orderDetails);
        message.setFrom("tharunj2003@gmail.com"); // Replace with your email

        mailSender.send(message);
        
    
    }
}
