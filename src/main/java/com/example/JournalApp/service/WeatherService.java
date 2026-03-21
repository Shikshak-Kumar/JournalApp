package com.example.JournalApp.service;

import com.example.JournalApp.apiResponse.WeatherApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiResponse getWeather(String city){
        String url = apiUrl + "?access_key=" + apiKey + "&query=" + city;
        try{
            return restTemplate.getForObject(url,WeatherApiResponse.class);
        } catch (Exception e){
            log.error("Error fetching weather for city {}: ", city, e);
            return null;
        }
    }

    public String getWeatherSummary(String city, String username) {
        WeatherApiResponse response = getWeather(city);
        if (response != null && response.getCurrent() != null) {
            WeatherApiResponse.Current current = response.getCurrent();

            int feelsLike = current.getFeelslike();
            String condition = String.join(", ", current.getWeatherDescriptions());
            String wind = current.getWindDir();

            return String.format("Hi %s! in %s it feels like %d°C, condition is %s, wind is %s.",
                    username, city, feelsLike, condition, wind);

        }
        return "Weather data not available";
    }
}
