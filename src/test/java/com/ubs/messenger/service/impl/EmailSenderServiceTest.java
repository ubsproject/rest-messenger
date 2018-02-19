package com.ubs.messenger.service.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.ubs.messenger.domain.EmailMessage;
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
public class EmailSenderServiceTest {

    private Logger logger = (Logger) LoggerFactory.getLogger(EmailSenderService.class);

    @Mock
    private Appender appender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Before
    public void setup() {
        logger.addAppender(appender);
    }

    @Test
    public void testSend_validPayload_shouldLogMessage() {
        //given
        String msg = "text";

        // when
        emailSenderService.send(EmailMessage.builder().textContent(msg).build());

        // then
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals("Successfully published email message: text", argument.getValue().getFormattedMessage());
        assertEquals(Level.INFO, argument.getValue().getLevel());
    }

    @Test
    public void testSend_nullPayload_stillLogsTheMessage() {
        //given
        String msg = null;

        // when
        emailSenderService.send(EmailMessage.builder().textContent(msg).build());

        // then
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals("Successfully published email message: null", argument.getValue().getFormattedMessage());
        assertEquals(Level.INFO, argument.getValue().getLevel());
    }
}
