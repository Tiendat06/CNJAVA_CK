package com.java.service.log;

import com.java.config.singleton.LogManager;
import com.java.models.LogHistory;
import com.java.models.MyUserDetail;
import com.java.repository.LogHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LogHistoryService {

    @Autowired
    public LogHistoryRepository logHistoryRepository;

    public void addLogHistory(LogHistory logHistory){
        logHistoryRepository.save(logHistory);
    }

    @EventListener
    public void handleSuccessfulLogin(AuthenticationSuccessEvent event) {
//        String username = event.getAuthentication().getName();

        MyUserDetail myUserDetail = (MyUserDetail) event.getAuthentication().getPrincipal();
        String user_id = myUserDetail.getCombinedUser().getUser().getUser_id();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String log_id = AUTO_LOG_ID();

        LogManager logManager = LogManager.getInstance();
        logManager.setLog(new LogHistory(log_id, user_id, currentTimestamp, "LOGIN"));
    }

    @EventListener
    public void handleLogoutSuccess(LogoutSuccessEvent event) {
//        String username = event.getAuthentication().getName();
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_id = myUserDetail.getCombinedUser().getUser().getUser_id();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        LogManager logManager = LogManager.getInstance();
        String log_id = AUTO_LOG_ID();

        logManager.setLog(new LogHistory(log_id, user_id, currentTimestamp, "LOGOUT"));
    }

    public String AUTO_LOG_ID(){
        String maxID = logHistoryRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("LOG%07d", number);
        }
        return "LOG0000001";
    }
}
