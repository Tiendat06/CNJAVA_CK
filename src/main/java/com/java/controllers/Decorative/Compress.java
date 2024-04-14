package com.java.controllers.Decorative;

import com.java.models.User;

import java.util.List;

public class Compress extends CompressDecorator {
    public Compress(Export wrapObj) {
        super(wrapObj);
    }

    @Override
    public byte[] export(List<User> userList) {
        return new byte[0];
    }
}
