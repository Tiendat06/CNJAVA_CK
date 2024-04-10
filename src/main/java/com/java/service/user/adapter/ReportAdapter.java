package com.java.service.user.adapter;

import com.java.models.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportAdapter implements IOldReport {

    private INewReport iNewReport;
    public ReportAdapter(INewReport report){
        this.iNewReport = report;
    }
    @Override
    public ResponseEntity<byte[]> exportReportOldMethod(List<User> userList) {
        return iNewReport.exportReportNewMethod(userList);
    }
}
