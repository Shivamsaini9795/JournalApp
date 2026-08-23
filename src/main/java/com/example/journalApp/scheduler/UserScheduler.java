package com.example.journalApp.scheduler;

import com.example.journalApp.Repository.UserRepository;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Service.EmailService;
import com.example.journalApp.Service.SentimentAnalysisService;
import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private SentimentAnalysisService analysisService;

   // @Scheduled(cron = "*/10 * * * * *")
    //@Scheduled(cron = "0 * * ? * *")
     @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> FilterdEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getContent()).collect(Collectors.toList());
            String entry = String.join("", FilterdEntries);
            String sentiment = analysisService.getSentiment(entry);
            emailService.SendMail(user.getEmail(),"Sentiment for last 7 days",sentiment);
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache()
    {
        appCache.init();
    }
}
