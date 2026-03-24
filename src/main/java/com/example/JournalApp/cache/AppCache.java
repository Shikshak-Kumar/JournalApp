package com.example.JournalApp.cache;

import com.example.JournalApp.entity.ConfigJournalApp;
import com.example.JournalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AppCache {
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public enum keys{
        WEATHER_API;
    }

    public static Map<String,String> appCache ;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();

        for(ConfigJournalApp configJournalApp : all){
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
        log.info("AppCache loaded: {}", appCache);
    }
}
