package com.ubs.messenger.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class MessageController {

        @RequestMapping("/")
        String home() {
            return "Module!";
        }
}
