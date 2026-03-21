package com.example.JournalApp.controller;

import com.example.JournalApp.apiResponse.WeatherApiResponse;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.UserService;
import com.example.JournalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/debug-role")
    public Object debug(Authentication authentication){
        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherApiResponse> getWeather(@RequestParam String city){
        try {
            WeatherApiResponse response = weatherService.getWeather(city);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
