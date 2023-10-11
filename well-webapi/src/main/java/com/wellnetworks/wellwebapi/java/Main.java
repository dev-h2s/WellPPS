package com.wellnetworks.wellwebapi.java;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wellnetworks")
public class Main {
    public static void main(String[] args) {
        System.out.println("well-webapi");
    }
}