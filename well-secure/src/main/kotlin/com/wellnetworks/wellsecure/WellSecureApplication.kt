package com.wellnetworks.wellsecure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.wellnetworks.wellcore"])
class WellSecureApplication

fun main(args: Array<String>) {
    runApplication<WellSecureApplication>(*args)
}