package com.ubs.messenger.application;

import com.ubs.messenger.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass=true)
public class WebApplication {
    public static void main(String[] args){
        SpringApplication.run(ApplicationConfiguration.class, args);
    }
}
