package com.java.service.account.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class MailSenderSubject {
    protected List<IMailSenderObserver> observers = new ArrayList<>();
    protected MailSenderSubject(){

    }

    public void RegisterObserver(IMailSenderObserver observer){
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public void UnregisterObserver(IMailSenderObserver observer){
        observers.remove(observer);
    }

    public abstract void dataChanged();
}
