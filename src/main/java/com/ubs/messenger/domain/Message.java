package com.ubs.messenger.domain;

import java.time.LocalDateTime;

public interface Message {

    LocalDateTime getSentTime();

    String getPayload();
}
