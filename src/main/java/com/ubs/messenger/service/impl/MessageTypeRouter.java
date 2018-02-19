package com.ubs.messenger.service.impl;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.domain.EmailMessage;
import com.ubs.messenger.domain.Message;
import com.ubs.messenger.service.MessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static com.ubs.messenger.service.impl.MapCacheRepository.MESSAGE_CACHE_NAME;

@Service
public class MessageTypeRouter implements MessageRouter{

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    @CachePut(cacheNames = MESSAGE_CACHE_NAME)
    public Message route(InputMessage msg) {
        Assert.notNull(msg.getMessageType(), "MessageType must be provided");
        Message message;

        switch (msg.getMessageType()){
            case EMAIL:
                message = EmailMessage.builder().sentTime(LocalDateTime.now()).textContent(msg.getText()).build();
                emailSenderService.send(message);
                break;
            default: throw new RuntimeException(String.format("Unsupported message type exception: %s", msg.getMessageType()));
        }

        return message;
    }
}
