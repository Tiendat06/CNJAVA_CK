package com.java.config.singleton;

import com.java.models.LogHistory;
import com.java.service.log.LogHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class LogManager {
    private volatile static LogManager uniqueInstance;

    @Autowired
    private LogHistoryService logHistoryService;
    private LogManager() {}

    @Bean
    public static LogManager getInstance() {
        if(uniqueInstance == null){
            synchronized (LogManager.class){
                if (uniqueInstance == null){
                    uniqueInstance = new LogManager();
                }
            }
        }
        return uniqueInstance;
    }

    public synchronized void setLog(LogHistory log){
        logHistoryService.addLogHistory(log);
    }
}
