package com.example.journalApp.scheduler;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Service.EmailService;
import com.example.journalApp.Service.SentimentAnalysisService;
import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.enums.Sentiment;
import com.example.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    //@Scheduled(cron = "0 * * ? * *")
     @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments){
                if(sentiment!=null){
                    sentimentCount.put(sentiment,sentimentCount.getOrDefault(sentiment,0)+1);
                }
            }

            Sentiment mostFrequentSentiment=null;
            int MaxCount=0;
            for(Map.Entry<Sentiment,Integer> entry: sentimentCount.entrySet()){
                if (entry.getValue() > MaxCount){
                    MaxCount= entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }

            if(mostFrequentSentiment!=null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
               try {
                   kafkaTemplate.send("weekly-sentiment", sentimentData.getEmail(), sentimentData);
               }catch (Exception e){
                   emailService.SendMail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
               }
            }
        }
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache()
    {
        appCache.init();
    }
}
