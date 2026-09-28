package com.example.journalApp.controller;

import com.example.journalApp.Service.CustomUserDetailServiceImpl;
import com.example.journalApp.Service.UserService;
import com.example.journalApp.dto.UserDTO;
import com.example.journalApp.entity.User;
import com.example.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name="Public APIs")
@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

     @Autowired
     private AuthenticationManager authenticationManager;

     @Autowired
     private CustomUserDetailServiceImpl service;

     @Autowired
     private JwtUtil jwtUtil;

    @GetMapping("healthcheck")
    public String show()
    {
        log.info("Health is Ok !");
        return "Ok";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user)
    {
        User Newuser= new User();
        Newuser.setEmail(user.getEmail());
        Newuser.setUserName(user.getUserName());
        Newuser.setPassword(user.getPassword());
        Newuser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(Newuser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user)
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = service.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }

    }
}
