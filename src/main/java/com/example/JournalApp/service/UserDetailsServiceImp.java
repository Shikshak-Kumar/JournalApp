package com.example.JournalApp.service;
import com.example.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.JournalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//This class connects Spring Security authentication with your MongoDB users.
//Its job is to load a user from the database when someone tries to log in.

//Think of it as the bridge between Spring Security and your User collection.

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // This allows the class to access MongoDB users.

    @Override
   public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUserName(username); // This searches your MongoDB collection.
        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder() // Your database user must be converted into a Spring Security user object.
                    .username(user.getUserName()) // Spring Security stores the username.
                    .password(user.getPassword()) // Password is already encrypted with BCrypt.
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username : " + username);
    }
}
