package com.java.service.user;

import com.java.models.User;
import com.java.repository.AccountRepository;
import com.java.repository.UserRepository;
import com.java.service.adapter_v1.IProvinceAPI;
import com.java.service.adapter_v1.ProvinceAPIAdapter;
import com.java.service.adapter_v1.ThirdPartyAdaptee;
import com.java.service.user.adapter.IOldReport;
import com.java.service.user.adapter.NewReport;
import com.java.service.user.adapter.OldReport;
import com.java.service.user.adapter.ReportAdapter;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Page<User> getUserPagination(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(String id, User user){
        userRepository.updateUser(id, user);
    }
    public void updateProfile(String id, User user){
        userRepository.updateProfile(id, user);
    }
    public User getAccIDByUserID(String id){
        return userRepository.findUserByID(id);
    }

    public List<Object[]> getUserProfile(String id){
        return userRepository.findUserProfileByUserId(id);
    }

    public void deleteByID(String id){
        userRepository.deleteById(id);
    }

    public String AUTO_USER_ID(){
        String maxID = userRepository.maxID();
        if (maxID != null) {
            int number = Integer.parseInt(maxID.substring(3)) + 1;
            return String.format("USE%07d", number);
        }
        return "USE0000001";
    }

    public List<String> getProvinceAPI() throws IOException {
        IProvinceAPI iProvinceAPI = new ProvinceAPIAdapter(new ThirdPartyAdaptee());
        return iProvinceAPI.getProvinceAPI();
    }

    public ResponseEntity<byte[]> exportUserReport(List<User> userList){
//        ADAPTER PATTERN V2 [TTD]
        IOldReport report = null;
        report = new ReportAdapter();
//        if(choice.equals("CSV")){
//        }else if(choice.equals("XLSX")){
//            report = new OldReport();
//        }else{
//            return null;
//        }
        return report.exportReportOldMethod(userList);
    }

    public void sendEmail(User user, String url) {

        String from = "phanluonghuy4623@gmail.com";
        String to = user.getEmail();
        String subject = "Account Verfication";
        String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Final Project with love <3";

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Final Project");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFirst_name() + " " + user.getLast_name());
            String siteUrl = url + "/log/verify?code=" + accountRepository.findVerifyCodeByUserId(user.getUser_id());

            System.out.println(siteUrl);

            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}