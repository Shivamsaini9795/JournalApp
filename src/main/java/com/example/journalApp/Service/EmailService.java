package com.example.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void SendMail(String to,String subject,String body)
    {
        try {
            SimpleMailMessage Mail = new SimpleMailMessage();
            Mail.setTo(to);
            Mail.setSubject(subject);
            Mail.setText(body);
            javaMailSender.send(Mail);
        }catch (Exception e )
        {
            log.error("Exception while send Email ",e);
        }
    }
}
