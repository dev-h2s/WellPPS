package com.wellnetworks.wellwebapi.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebapiMain {
    public static void main(String[] args) {
        SpringApplication.run(WebapiMain.class, args);
    }
}