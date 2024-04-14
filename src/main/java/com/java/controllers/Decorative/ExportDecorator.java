package com.java.controllers.Decorative;

import com.java.models.User;

import java.util.List;

public abstract class ExportDecorator  implements Export{
    protected Export wrapObj;
    public ExportDecorator (Export wrapObj) {
        this.wrapObj = wrapObj;
    }

    @Override
    public byte[] export(List<User> userList) {
        // Delegate export operation to decorated export object
        return wrapObj.export(userList);
    }
}
