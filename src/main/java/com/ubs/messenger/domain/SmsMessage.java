package com.ubs.messenger.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
public class SmsMessage implements Message {

    private String phoneNo;

    private LocalDateTime sentTime;

    private String textContent;

    @Override
    public LocalDateTime getSentTime() {
        return sentTime;
    }

    @Override
    public String getPayload() {
        return textContent;
    }

    public String getPhoneNo(){
        return phoneNo;
    }
}
