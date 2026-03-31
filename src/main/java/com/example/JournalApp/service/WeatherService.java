package com.example.JournalApp.service;

import com.example.JournalApp.apiResponse.WeatherApiResponse;
import com.example.JournalApp.cache.AppCache;
import com.example.JournalApp.constants.Placeholders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    //    Mocking the Weatherstack data:
    private WeatherApiResponse getMockWeather(String city){

        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Current current = new WeatherApiResponse.Current();

        current.setObservationTime("01:00 PM");
        current.setTemperature(99 + (int)(Math.random() * 5)); // dynamic temp
        current.setWeatherCode(116);
        current.setWeatherIcons(List.of(
                "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0002_sunny_intervals.png"
        ));
        current.setWeatherDescriptions(List.of("Partly cloudy"));
        current.setWindSpeed(10);
        current.setWindDegree(250);
        current.setWindDir("W (mock data)");
        current.setFeelslike(100);
        current.setUvIndex(6);
        current.setVisibility(6);
        current.setIsDay("yes");

        response.setCurrent(current);

        return response;
    }

    public WeatherApiResponse getWeather(String city){
        WeatherApiResponse weatherApiResponse = redisService.get("weather_of_" + city, WeatherApiResponse.class);
        if(weatherApiResponse!=null){
            return weatherApiResponse;
        }
        String url = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY,apiKey).replace(Placeholders.CITY,city);
        try{
//            WeatherApiResponse body = restTemplate.getForObject(url,WeatherApiResponse.class);
            WeatherApiResponse body = getMockWeather(city);
            if(body!=null){
                redisService.set("weather_of_"+city,body,300l);  // 300 secs long
            }
            return body;
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
