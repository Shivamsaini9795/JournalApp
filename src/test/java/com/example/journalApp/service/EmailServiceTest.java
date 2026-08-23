package com.example.journalApp.service;

import com.example.journalApp.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class EmailServiceTest {

    @Autowired
    private EmailService service;

    @Test
    void  TestSendMail()
    {
        service.SendMail("shivamsaini01364@gmail.com","Testing Java Mail Sender","Hi,How are you Shivam ?");
    }
}
