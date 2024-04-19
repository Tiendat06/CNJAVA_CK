package com.java.config.singleton;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private volatile static CacheManager uniqueInstance;
    private Map<String, Object> cache;

    private CacheManager(){
        cache = new HashMap<>();
    }

    public static CacheManager getInstance(){
        if(uniqueInstance == null){
            synchronized (CacheManager.class){
                if (uniqueInstance == null){
                    uniqueInstance = new CacheManager();
                }
            }
        }
        return uniqueInstance;
    }

    public synchronized void setCache(String key, Object value){
        cache.put(key, value);
    }

    public synchronized Object getCache(String key){
        return cache.get(key);
    }

    public synchronized void removeCache(String key){
        cache.remove(key);
    }
}
