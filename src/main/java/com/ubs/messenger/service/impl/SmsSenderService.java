package com.ubs.messenger.service.impl;

import com.ubs.messenger.domain.SmsMessage;
import com.ubs.messenger.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SmsSenderService implements SenderService<SmsMessage> {

    private static final Logger log = LoggerFactory.getLogger(SmsSenderService.class);

    @Override
    public void send(SmsMessage message) {
        Assert.hasLength(message.getPhoneNo(), "Cannot sent sms without phone no.");
        log.info("Sms has been successfully sent to number: {}, message: {}", message.getPhoneNo(), message.getPayload());
    }
}
