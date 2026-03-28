package com.example.JournalApp.scheduler;

import com.example.JournalApp.cache.AppCache;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepositoryImpl;
import com.example.JournalApp.service.EmailService;
import com.example.JournalApp.service.UserSentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private UserSentimentAnalysisService userSentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        log.info("Cron job started: fetching users for sentiment analysis...");
        List<User> users = userRepository.getUserForSA();

        Date sevenDaysAgo = Date.from(
                LocalDateTime.now()
                        .minusDays(7)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(x -> x.getDate() != null && x.getDate().after(sevenDaysAgo))
                    .map(x-> x.getContent())
                    .collect(Collectors.toList());

            String entry = String.join(" ",filteredEntries);
            String sentiment = userSentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days",sentiment);
            log.info("Sent sentiment analysis for this user: {}", user.getUserName());
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
