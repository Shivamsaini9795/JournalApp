package com.example.journalApp.Service;

import com.example.journalApp.Repository.UserRepository;
import com.example.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("hahahahahahaha");
            log.warn("hahahahahahaha");
            log.info("hahahahahahaha");
            log.debug("hahahahahahaha");
            log.trace("hahahahahahaha");

            return false;
        }
    }

    public void saveAdmin(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public List<User> getAll()
    {
       return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userRepository.findById(id);
    }

    public  void deleteById(ObjectId id)
    {
         userRepository.deleteById(id);
    }

    public User findByUsername(String userName)
    {
        return userRepository.findByUserName(userName);
    }

}
