plugins {
    id("armeria-demo.java-conventions")
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.io.spring.dependency.management)
}

dependencies {
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("com.linecorp.armeria:armeria-spring-boot3-actuator-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
