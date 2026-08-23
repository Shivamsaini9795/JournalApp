package com.example.journalApp.cache;

import com.example.journalApp.Repository.ConfigJournalAppRepository;
import com.example.journalApp.entity.ConfigJournalAppEntry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{

        weather_api;
    }

    public Map<String,String> App_Cache =  new HashMap<>();

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init() {
        refreshCache();
    }

    public void refreshCache() {
        App_Cache.clear();

        List<ConfigJournalAppEntry> all =
                configJournalAppRepository.findAll();

        for (ConfigJournalAppEntry entry : all) {
            App_Cache.put(entry.getKey(), entry.getValue());
        }
    }
}
