package com.java.controllers.Decorative;

import com.java.models.User;

import java.util.List;

public interface Export {
     byte[] export(List<User> userList);
}
