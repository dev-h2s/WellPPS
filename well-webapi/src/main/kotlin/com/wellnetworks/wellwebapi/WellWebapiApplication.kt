package com.wellnetworks.wellwebapi

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication(scanBasePackages = ["com.wellnetworks"])
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
//@ComponentScan(basePackages = ["com.wellnetworks.wellcore", "com.wellnetworks.wellsecure"])
class WellWebapiApplication

fun main(args: Array<String>) {
	configureApplication(SpringApplicationBuilder()).run(*args)
//	runApplication<WellWebapiApplication>(*args)
}

fun configureApplication(builder: SpringApplicationBuilder): SpringApplicationBuilder {
	return builder.sources(WellWebapiApplication::class.java)
}