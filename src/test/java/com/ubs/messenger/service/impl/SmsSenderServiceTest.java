package com.ubs.messenger.service.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.ubs.messenger.domain.SmsMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SmsSenderServiceTest {

    public static final String PHONE_NO = "+48 323 3232";
    private Logger logger = (Logger) LoggerFactory.getLogger(SmsSenderService.class);

    @Mock
    private Appender appender;

    @InjectMocks
    private SmsSenderService smsSenderService;

    @Before
    public void setup() {
        logger.addAppender(appender);
    }

    @Test
    public void testSend_validPayload_shouldLogMessage() {
        //given
        String msg = "text";

        // when
        smsSenderService.send(SmsMessage.builder().textContent(msg).phoneNo(PHONE_NO).build());

        // then
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals("Sms has been successfully sent to number: +48 323 3232, message: text", argument.getValue().getFormattedMessage());
        assertEquals(Level.INFO, argument.getValue().getLevel());
    }

    @Test
    public void testSend_nullPayload_stillLogsTheMessage() {
        //given
        String msg = null;

        // when
        smsSenderService.send(SmsMessage.builder().textContent(msg).phoneNo("+48 323 3232").build());

        // then
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals("Sms has been successfully sent to number: +48 323 3232, message: null", argument.getValue().getFormattedMessage());
        assertEquals(Level.INFO, argument.getValue().getLevel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSend_nullPhoneNo_throwsIllegalArgumentException() {
        //given
        String phoneNo = null;

        // when
        smsSenderService.send(SmsMessage.builder().textContent("text").phoneNo(phoneNo).build());

        // then
    }
}
