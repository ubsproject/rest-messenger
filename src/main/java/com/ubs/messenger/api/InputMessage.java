package com.ubs.messenger.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class InputMessage {

    @NotEmpty
    private String text;

    @NotNull
    private MessageType messageType;

    @NotNull
    private String recipient;
}
