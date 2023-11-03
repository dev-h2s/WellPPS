package com.wellnetworks.wellsecure.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//        (exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wellnetworks.wellcore"})
public class SecureMain {
    public static void main(String[] args) {
        SpringApplication.run(SecureMain.class, args);
    }
}