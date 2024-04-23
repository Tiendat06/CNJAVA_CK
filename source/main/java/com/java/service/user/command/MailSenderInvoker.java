package com.java.service.user.command;

import java.util.LinkedList;
import java.util.Queue;

public class MailSenderInvoker {
    private final Queue<ICommand> commands;

    public MailSenderInvoker(){
        this.commands = new LinkedList<>();
    }

    public void setCommand(ICommand cmd){
        commands.add(cmd);
    }

    public void execute(){
        while (!commands.isEmpty()){
            ICommand cmd = commands.poll();
            cmd.execute();
        }
    }
}
