package com.ubs.messenger.service.impl;

import com.ubs.messenger.domain.Message;
import com.ubs.messenger.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements SenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

    @Override
    public void send(Message message) {
        log.info("Successfully published email message: {}", message.getPayload());
    }
}
