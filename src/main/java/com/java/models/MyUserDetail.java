package com.java.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
@Getter
@Setter
public class MyUserDetail implements UserDetails {
    private CombinedUser combinedUser;
    public MyUserDetail(CombinedUser combinedUser) {
        this.combinedUser = combinedUser;
    }

    public MyUserDetail() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(combinedUser.getRole().getRole_name()));
    }

    @Override
    public String getPassword() {
        return combinedUser.getAccount().getPassword() ;
    }

    @Override
    public String getUsername() {
        return combinedUser.getUser().getFirst_name() + " " + combinedUser.getUser().getLast_name();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return combinedUser.getAccount().isStatus();
    }
}