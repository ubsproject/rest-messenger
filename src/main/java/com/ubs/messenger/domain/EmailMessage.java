package com.ubs.messenger.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage implements Message {

    private String textContent;

    @Override
    public String getPayload() {
        return textContent;
    }
}
