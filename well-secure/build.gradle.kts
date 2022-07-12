import org.jetbrains.kotlin.ir.backend.js.compile

dependencies {
    implementation(project(":well-core"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.session:spring-session-core")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.security:spring-security-test")

}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}