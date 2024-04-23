package com.java.models;

public class CombinedUser {
    private User user;
    private Account account;
    private Role role;

    public CombinedUser(User user, Account account, Role role) {
        this.user = user;
        this.account = account;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
