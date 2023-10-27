package com.wellnetworks.wellcore.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CoreMain {
    public static void main(String[] args) {
        SpringApplication.run(CoreMain.class, args);
    }
}