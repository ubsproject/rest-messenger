package com.ubs.messenger.service.impl;

import com.ubs.messenger.domain.EmailMessage;
import com.ubs.messenger.domain.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.ubs.messenger.service.CacheRepository.MESSAGE_CACHE_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapCacheRepositoryTest {

    @Mock
    CacheManager cacheManager;

    @Mock
    Cache cache;

    @InjectMocks
    MapCacheRepository mapCacheRepository;

    ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();

    @Before
    public void setup(){
        when(cacheManager.getCache(eq(MESSAGE_CACHE_NAME))).thenReturn(cache);
        when(cache.getNativeCache()).thenReturn(map);
        Message msg1 = produceMessage("txt1");
        Message msg2 = produceMessage("txt2");
        Message msg3 = produceMessage("txt3");
        map.putIfAbsent(msg1.hashCode(), msg1);
        map.putIfAbsent(msg2.hashCode(), msg2);
        map.putIfAbsent(msg3.hashCode(), msg3);
    }

    @Test
    public void testGetAllEntries_returnsThreeMessages(){
        //given - map cache

        //when
        List<?> messages = mapCacheRepository.getAllEntries();

        //then
        assertEquals(3, messages.size());
    }

    private Message produceMessage(String txt){
        return EmailMessage.builder().textContent(txt).sentTime(LocalDateTime.now()).build();
    }
}
