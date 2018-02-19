package com.ubs.messenger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Component
public class MapCacheRepository implements com.ubs.messenger.service.CacheRepository {

    @Autowired
    private CacheManager cacheManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<?> getAllEntries(){
        Cache cache = cacheManager.getCache(MESSAGE_CACHE_NAME);
        ConcurrentMap<Object, Object> map = (ConcurrentMap) cache.getNativeCache();
        return new ArrayList<>(map.values());
    }

}
