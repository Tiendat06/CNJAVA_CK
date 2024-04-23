package com.java.repository;

import com.java.models.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHistoryRepository extends JpaRepository<LogHistory, String> {
    @Query("select max (log_id) from log_history ")
    String maxID();


}
