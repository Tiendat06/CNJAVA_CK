package com.java.service.user.adapter;

import com.java.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReportAdapter implements IXLSXReport {

    private ICSVReport ICSVReport;
    public CSVReportAdapter(){
        this.ICSVReport = new CSVReport();
    }
    @Override
    public ResponseEntity<byte[]> exportReportXLSXMethod(List<User> userList) {
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
        return ICSVReport.exportReportCSVMethod(userListData);
    }
}
