package com.java.service.catalog.command;

public interface ICommand {

    abstract void execute();
    abstract void undo();
}
