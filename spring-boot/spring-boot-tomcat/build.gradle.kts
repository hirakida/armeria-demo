plugins {
    id("armeria-demo.spring-boot-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("com.linecorp.armeria:armeria-tomcat9")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
