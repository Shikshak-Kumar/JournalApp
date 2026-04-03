package com.example.JournalApp.controller;

import com.example.JournalApp.apiResponse.WeatherApiResponse;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.UserDetailsServiceImp;
import com.example.JournalApp.service.UserService;
import com.example.JournalApp.service.WeatherService;
import com.example.JournalApp.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>("signup succefful for the user: "+user.getUserName(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()));

            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(user.getUserName());
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occured while createAuthenticationToken", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/debug-role")
    public Object debug(Authentication authentication) {
        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities());
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherApiResponse> getWeather(@RequestParam String city) {
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
