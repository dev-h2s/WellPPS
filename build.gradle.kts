import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

}

plugins {
    val kotlinVersion: String by System.getProperties()
    val springBootVersion: String by System.getProperties()
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
}

subprojects {

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }

    group = "org.wellnetworks"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val testRuntimeOnly by configurations
        val compileOnly by configurations
        val annotationProcessor by configurations
        val developmentOnly by configurations
        val queryDslVersion: String by System.getProperties()
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        developmentOnly("org.springframework.boot:spring-boot-devtools")
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":well-core") {
    tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") { enabled = false }
    tasks.named<Jar>("jar") { enabled = true }

    dependencies {
        val implementation by configurations
        val runtimeOnly by configurations
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.security:spring-security-core:5.7.5")
        runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    }
}

project(":well-secure") {
    tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") { enabled = false }
    tasks.named<Jar>("jar") { enabled = true }

    dependencies {
        val implementation by configurations
        val runtimeOnly by configurations
        val testImplementation by configurations
        implementation(project(":well-core"))
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.springframework.session:spring-session-core")
        implementation("io.jsonwebtoken:jjwt-api:0.11.5")
        implementation("com.auth0:java-jwt:4.2.1")
        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
        testImplementation("org.springframework.security:spring-security-test")

    }
}

project(":well-webapi") {
    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        implementation(project(":well-core"))
        implementation(project(":well-secure"))
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.springframework.session:spring-session-core")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
        testImplementation("org.springframework.security:spring-security-test")
    }

    tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") { enabled = true }
    tasks.named<Jar>("jar") { enabled = true }
}
