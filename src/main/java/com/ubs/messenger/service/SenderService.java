package com.ubs.messenger.service;

import com.ubs.messenger.domain.Message;

public interface SenderService<T extends Message> {
    void send(T message);
}
