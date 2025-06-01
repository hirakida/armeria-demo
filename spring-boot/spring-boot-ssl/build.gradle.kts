plugins {
    id("armeria-demo.java-conventions")
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.io.spring.dependency.management)
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
