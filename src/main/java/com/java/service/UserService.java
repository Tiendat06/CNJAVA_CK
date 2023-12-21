package com.java.service;

import com.java.models.User;
import com.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public Page<User> getUserPagination(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);
    }

    public void updateUser(String id, User user){
        userRepository.updateUser(id, user);
    }

    public User getAccIDByUserID(String id){
        return userRepository.findUserByID(id);
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

}
