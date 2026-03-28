package com.example.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testEmailService(){
        emailService.sendEmail("example@gmail.com",
                "Testing Java mail sender",
                "Hi, aap kaise hain?"
        );
    }
}
