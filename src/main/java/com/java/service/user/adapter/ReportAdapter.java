package com.java.service.user.adapter;

import com.java.models.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportAdapter implements IOldReport {

    private INewReport iNewReport;
    public ReportAdapter(){
        this.iNewReport = new NewReport();
    }
    @Override
    public ResponseEntity<byte[]> exportReportOldMethod(List<User> userList) {
        List<String[]> userListData = new ArrayList<>();
        for (User user: userList){
            userListData.add(new String[]{
                    user.getUser_id(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getEmail(),
                    user.getPhone_number(),
                    user.getAddress(),
                    user.getBirthday().toString(),
                    user.getGender()
            });
        }
        return iNewReport.exportReportNewMethod(userListData);
    }
}
