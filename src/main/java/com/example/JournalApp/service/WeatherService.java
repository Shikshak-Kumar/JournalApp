package com.example.JournalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    public String getWeather(String city){
        String url =
    }
}
