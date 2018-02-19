package com.ubs.messenger.service;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.domain.Message;

public interface MessageRouter {
    Message route(InputMessage msg);
}
