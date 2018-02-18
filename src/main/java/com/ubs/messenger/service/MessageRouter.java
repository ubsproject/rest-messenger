package com.ubs.messenger.service;

import com.ubs.messenger.api.InputMessage;

public interface MessageRouter {
    void route(InputMessage msg);
}
