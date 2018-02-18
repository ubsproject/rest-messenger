package com.ubs.messenger.application;

import com.ubs.messenger.controller.MessageController;
import org.springframework.boot.SpringApplication;

public class WebApp {
    public static void main(String[] args){
        SpringApplication.run(MessageController.class, args);
    }
}
