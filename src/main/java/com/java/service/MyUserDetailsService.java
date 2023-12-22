package com.java.service;

import com.java.models.*;
import com.java.repository.AccountRepository;
import com.java.repository.RoleRepository;
import com.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        Account account = accountRepository.findByAccountId(user.getAccount_id());
        Role role = roleRepository.findByRoleId(account.getRole_id());
        CombinedUser combinedUser = new CombinedUser(user,account,role);

        return new MyUserDetail(combinedUser);
    }
}
