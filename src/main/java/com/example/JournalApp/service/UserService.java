package com.example.JournalApp.service;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // private static final Logger logger = LoggerFactory.getLogger(UserService.class); // no need to write this line if @Slf4j used;


    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error occured while creating user {} : ", user.getUserName(),e);
//            logger.info("Haan bhsdk ek aur ghusa le"); // agar instance lia to "logger" agar lombok se @Slf4j lia to "log"
//            logger.warn("Haan bhsdk ek aur ghusa le");
//          customize ke baad chalenge
            log.debug("Haan bhsdk ek aur ghusa le");
            log.trace("Haan bhsdk ek aur ghusa le");


            return false;
        }
    }

    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }




}
