plugins {
    id("armeria-demo.spring-boot-conventions")
}

dependencies {
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("com.linecorp.armeria:armeria-prometheus1")
    implementation("org.hibernate.validator:hibernate-validator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
