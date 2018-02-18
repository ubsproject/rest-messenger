package com.ubs.messenger.service;

import com.ubs.messenger.domain.Message;

public interface SenderService {

    void send(Message message);
}
