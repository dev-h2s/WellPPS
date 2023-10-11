package com.wellnetworks.wellchargeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ChargeMain {
    public static void main(String[] args) {
        System.out.println("well_core");
        SpringApplication.run(ChargeMain.class, args);
    }
}