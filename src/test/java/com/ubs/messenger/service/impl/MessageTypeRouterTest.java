package com.ubs.messenger.service.impl;


import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.api.MessageType;
import com.ubs.messenger.domain.Message;
import com.ubs.messenger.domain.SmsMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageTypeRouterTest {

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private SmsSenderService smsSenderService;

    @InjectMocks
    private MessageTypeRouter messageTypeRouter;

    @Test
    public void testRoute_validEmailInput_shouldPassTxtIntoEmailSender(){
        //given
        String txt = "text message";
        InputMessage msg = InputMessage.builder().messageType(MessageType.EMAIL).text(txt).build();

        //when
        messageTypeRouter.route(msg);

        //then
        ArgumentCaptor<Message> msgCaptor = ArgumentCaptor.forClass(Message.class);
        verify(emailSenderService).send(msgCaptor.capture());
        assertEquals(txt, msgCaptor.getValue().getPayload());
    }

    @Test
    public void testRoute_validSmsInput_shouldPassTxtIntoEmailSender(){
        //given
        String txt = "text message";
        InputMessage msg = InputMessage.builder().messageType(MessageType.SMS).text(txt).build();

        //when
        messageTypeRouter.route(msg);

        //then
        ArgumentCaptor<SmsMessage> msgCaptor = ArgumentCaptor.forClass(SmsMessage.class);
        verify(smsSenderService).send(msgCaptor.capture());
        assertEquals(txt, msgCaptor.getValue().getPayload());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoute_noMessageType_throwsIllegalArgumentException(){
        //given
        InputMessage msg = InputMessage.builder().build();

        //when
        messageTypeRouter.route(msg);

        //then ex
    }

    @Test
    public void testRoute_noTextMessage_passesNullToSender(){
        //given
        InputMessage msg = InputMessage.builder().messageType(MessageType.EMAIL).build();

        //when
        messageTypeRouter.route(msg);

        //then
        ArgumentCaptor<Message> msgCaptor = ArgumentCaptor.forClass(Message.class);
        verify(emailSenderService).send(msgCaptor.capture());
        assertNull(msgCaptor.getValue().getPayload());
    }
}
