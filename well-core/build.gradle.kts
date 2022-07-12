dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.security:spring-security-core:5.6.6")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2:2.1.212")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}