package com.example.journalApp.service;

import com.example.journalApp.Repository.UserRepository;
import com.example.journalApp.Service.UserService;
import com.example.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
//    @CsvSource({
//            "Shivam",
//            "Soni",
//            "Roshan",
//            "Ankur"
//    })
    public void testSaveNewUser(User user)
    {
//        User user = userRepository.findByUserName("Roshan");
       assertTrue(userService.saveNewUser(user));
//        assertTrue(5>3);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "5,6,11",
            "20,15,35"
    })
    public void test(int a,int b, int expected)
    {
        assertEquals(expected,a+b);
    }
}
