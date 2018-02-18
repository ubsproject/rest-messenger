package com.ubs.messenger.integration;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.api.MessageType;
import com.ubs.messenger.config.ApplicationConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApplicationConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestIntegrationTest {

    @Before
    public void setup(){
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testSend_emailMessage_shouldReturnAccepted() {
        InputMessage msg = InputMessage.builder().messageType(MessageType.EMAIL).text("Simple message").build();

        given()//
            .body(msg)//
            .contentType(ContentType.JSON)//
        .when()//
            .post("/messenger/send")//
        .then()//
            .statusCode(HttpStatus.SC_CREATED);//
    }

    @Test
    public void testSend_noMessageType_shouldReturnBadRequest() {
        InputMessage msg = InputMessage.builder().text("Simple message").build();

        given()//
            .body(msg)//
            .contentType(ContentType.JSON)//
        .when()//
            .post("/messenger/send")//
        .then()//
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(containsString("NotNull.inputMessage.messageType"));//
    }

    @Test
    public void testSend_noText_shouldReturnBadRequest() {
        InputMessage msg = InputMessage.builder().messageType(MessageType.EMAIL).build();

        given()//
                .body(msg)//
                .contentType(ContentType.JSON)//
                .when()//
                .post("/messenger/send")//
                .then()//
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("NotEmpty.inputMessage.text"));//
    }
}