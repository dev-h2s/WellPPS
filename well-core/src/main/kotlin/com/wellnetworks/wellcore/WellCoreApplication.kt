package com.wellnetworks.wellcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WellCoreApplication

fun main(args: Array<String>) {
    runApplication<WellCoreApplication>(*args)
}
