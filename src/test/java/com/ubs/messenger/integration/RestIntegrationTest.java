package com.ubs.messenger.integration;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.api.MessageType;
import com.ubs.messenger.config.ApplicationConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.ubs.messenger.service.CacheRepository.MESSAGE_CACHE_NAME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApplicationConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestIntegrationTest {

    private static final String MESSAGES_PATH = "/messenger/messages";
    private static final String JSON_PARSE_ERROR = "JSON parse error";
    private static final String MESSAGE_TYPE_MMS_TEXT_TEST = "{ \"messageType\" : \"MMS\", \"text\": \"Test\"}";
    private static final InputMessage VALID_EMAIL_MSG = InputMessage.builder()//
        .messageType(MessageType.EMAIL)//
        .recipient("test@email.com")
        .text("Simple message")//
        .build();//
    private static final InputMessage VALID_SMS_MSG = InputMessage.builder()//
        .messageType(MessageType.SMS)//
        .recipient("+48 1234 1234")
        .text("Simple message")//
        .build();//

    @Autowired
    CacheManager cacheManager;

    @Before
    public void setup(){
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testSend_emailMessage_shouldReturnAccepted() {
        given()//
            .body(VALID_EMAIL_MSG)//
            .contentType(ContentType.JSON)//
        .when()//
            .post(MESSAGES_PATH)//
        .then()//
            .statusCode(HttpStatus.SC_CREATED)//
            .body("sentTime", notNullValue());//
    }

    @Test
    public void testSend_smsMessage_shouldReturnAccepted() {
        given()//
            .body(VALID_SMS_MSG)//
            .contentType(ContentType.JSON)//
        .when()//
            .post(MESSAGES_PATH)//
        .then()//
            .statusCode(HttpStatus.SC_CREATED)//
            .body("sentTime", notNullValue());//
    }

    @Test
    public void testSend_emailMessage_shouldCreateLocationHeader() {
        //given - email msg

        //when
        Headers headers = given()//
            .body(VALID_EMAIL_MSG)//
            .contentType(ContentType.JSON)//
            .when()//
            .post(MESSAGES_PATH)//
            .headers();//

        //then
        assertNotNull(headers.getValue("location"));
        assertThat(headers.getValue("location"), containsString("/messenger/messages/"));
    }

    @Test
    public void testSend_noMessageType_shouldReturnBadRequest() {
        InputMessage msg = VALID_EMAIL_MSG.toBuilder().messageType(null).build();

        given()//
            .body(msg)//
            .contentType(ContentType.JSON)//
        .when()//
            .post(MESSAGES_PATH)//
        .then()//
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(containsString("NotNull.inputMessage.messageType"));//
    }

    @Test
    public void testSend_noReceipient_shouldReturnBadRequest() {
        InputMessage msg = VALID_SMS_MSG.toBuilder().recipient(null).build();

        given()//
                .body(msg)//
                .contentType(ContentType.JSON)//
                .when()//
                .post(MESSAGES_PATH)//
                .then()//
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("NotNull.inputMessage.recipient"));//
    }

    @Test
    public void testSend_unexpectedMessageType_shouldReturnBadRequest() {
        given()//
            .body(MESSAGE_TYPE_MMS_TEXT_TEST)//
            .contentType(ContentType.JSON)//
        .when()//
            .post(MESSAGES_PATH)//
        .then()//
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(containsString(JSON_PARSE_ERROR));//
    }

    @Test
    public void testSend_noText_shouldReturnBadRequest() {
        InputMessage msg = VALID_EMAIL_MSG.toBuilder().text(null).build();

        given()//
            .body(msg)//
            .contentType(ContentType.JSON)//
        .when()//
            .post(MESSAGES_PATH)//
        .then()//
            .statusCode(HttpStatus.SC_BAD_REQUEST)//
            .body(containsString("NotEmpty.inputMessage.text"));//
    }

    @Test
    public void testSend_malformedJson_shouldReturnBadRequest() {
        given()//
           .body("{{malformed}")//
           .contentType(ContentType.JSON)//
        .when()//
           .post(MESSAGES_PATH)//
        .then()//
           .statusCode(HttpStatus.SC_BAD_REQUEST)
           .body(containsString(JSON_PARSE_ERROR));//
    }

    @Test
    public void testMessages_noMessages_returnsStatusOk() {
        //given - no elements

        //when
        int statusCode = given().when().get(MESSAGES_PATH).statusCode();

        //then
        assertEquals(HttpStatus.SC_OK, statusCode);
    }

    @Test
    public void testMessages_twoInputMessages_returnsList() {
        //given
        InputMessage msg1 = InputMessage.builder().messageType(MessageType.EMAIL).text("Simple message").recipient("em").build();
        InputMessage msg2 = InputMessage.builder().messageType(MessageType.SMS).text("Simple message2").recipient("123").build();
        given()//
                .body(msg1)//
                .contentType(ContentType.JSON)//
                .when()//
                .post(MESSAGES_PATH).then().body("sentTime", notNullValue());//

        given()//
                .body(msg2)//
                .contentType(ContentType.JSON)//
                .when()//
                .post(MESSAGES_PATH).then().body("sentTime", notNullValue());//

        //when
        List<?> messages = given().when().get(MESSAGES_PATH).getBody().as(List.class);

        //then
        assertEquals(2, messages.size());
    }

    @After
    public void cleanup(){
        cacheManager.getCache(MESSAGE_CACHE_NAME).clear();
    }
}
