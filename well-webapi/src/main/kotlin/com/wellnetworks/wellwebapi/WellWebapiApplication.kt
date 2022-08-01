package com.wellnetworks.wellwebapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(scanBasePackages = ["com.wellnetworks"])
//@ComponentScan(basePackages = ["com.wellnetworks.wellcore", "com.wellnetworks.wellsecure"])
class WellWebapiApplication

fun main(args: Array<String>) {
	runApplication<WellWebapiApplication>(*args)
}
