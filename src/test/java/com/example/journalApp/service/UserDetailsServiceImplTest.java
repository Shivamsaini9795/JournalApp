package com.example.journalApp.service;

import com.example.journalApp.Repository.UserRepository;
import com.example.journalApp.Service.CustomUserDetailServiceImpl;
import com.example.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImplTest {

    @InjectMocks
    private CustomUserDetailServiceImpl userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void SetUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest()
    {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Shivam").password("12345").roles(new ArrayList<>()).build());
        UserDetails user = userDetailService.loadUserByUsername("Shivam");
        Assertions.assertNotNull(user);

    }
}
