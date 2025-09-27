plugins {
    id("armeria-demo.spring-boot-conventions")
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
