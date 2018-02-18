package com.ubs.messenger.service.impl;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.domain.EmailMessage;
import com.ubs.messenger.service.MessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MessageTypeRouter implements MessageRouter{

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void route(InputMessage msg) {
        Assert.notNull(msg.getMessageType(), "MessageType must be provided");

        switch (msg.getMessageType()){
            case EMAIL: emailSenderService.send(EmailMessage.builder().textContent(msg.getText()).build());
                break;
            default: throw new RuntimeException(String.format("Unsupported message type exception: %s", msg.getMessageType()));
        }
    }
}
