package com.wellnetworks.wellsecure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wellnetworks.wellcore")
public class Main {
    public static void main(String[] args) {
        System.out.println("well-secure");
    }
}