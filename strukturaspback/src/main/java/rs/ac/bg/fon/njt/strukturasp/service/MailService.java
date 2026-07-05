/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Home PC
 */
@Service
public class MailService {
    
    private final JavaMailSender mail;
    @Value("${app.mail.from}")
    private String from;

    public MailService(JavaMailSender mail) {
        this.mail = mail;
    }
    
    public void send(String to,String subject,String text) throws MessagingException{
        
        //SimpleMailMessage
        
        MimeMessage message=mail.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
        
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
       
        helper.setText(text,true);
        mail.send(message);
    }
    
}
