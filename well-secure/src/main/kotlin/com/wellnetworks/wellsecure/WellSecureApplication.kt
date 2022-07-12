package com.wellnetworks.wellsecure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WellSecureApplication

fun main(args: Array<String>) {
    runApplication<WellSecureApplication>(*args)
}
