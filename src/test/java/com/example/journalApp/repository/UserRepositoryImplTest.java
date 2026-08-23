package com.example.journalApp.repository;

import com.example.journalApp.Repository.UserRepositoryImpl;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Test
    public void TestSaveNewUser()
    {
       Assertions.assertNotNull(userRepository.getUserForSA()) ;
    }
}
