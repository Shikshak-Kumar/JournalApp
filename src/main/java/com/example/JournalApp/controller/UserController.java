package com.example.JournalApp.controller;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import com.example.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userService.getAll();

        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("id/{myId}")
//    public ResponseEntity<?> getUserById(@PathVariable ObjectId myId){
//        Optional<User> entry = userService.findById(myId);
//
//        if(entry.isPresent()){
//            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



//    @DeleteMapping("id/{myId}")
//    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
//        try{
//            userService.deleteById(myId);
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByUserName(Authentication authentication){
        try{
            String userName = authentication.getName();
            userRepository.deleteByUserName(userName);
            return new ResponseEntity<>("User '" + userName + "' deleted successfully",HttpStatus.OK);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping // is authenticated then no need to write url
    public ResponseEntity<User> updateUser(Authentication authentication, @RequestBody User user){

        try{
            String userName = authentication.getName();
            User userInDb = userService.findByUserName(userName);
            if(userInDb!=null){
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());

            }
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb,HttpStatus.OK);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
