package com.example.JournalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ResourceBundle;

public class weatherApiResponse {
//    private Request request;
//    private Location location;
    private Current current;


    public class Current{
        @JsonProperty("observation_time")
        private String observationTime;

        private int temperature;

        @JsonProperty("weather_code")
        public int weatherCode;

        @JsonProperty("weather_icons")
        public List<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;

        @JsonProperty("wind_speed")
        public int windSpeed;

        @JsonProperty("wind_degree")
        public int windDegree;

        @JsonProperty("wind_dir")
        public String windDir;

        public int feelslike;

        @JsonProperty("uv_index")
        public int uvIndex;

        public int visibility;

        @JsonProperty("is_day")
        public String is_day;
    }

//    public class Location{
//        public String name;
//        public String country;
//        public String region;
//        public String lat;
//        public String lon;
//        public String localtime;
//    }
//
//    public class Request{
//        public String type;
//        public String query;
//        public String language;
//        public String unit;
//    }
}






