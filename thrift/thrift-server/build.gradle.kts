plugins {
    id("armeria-demo.spring-boot-conventions")
}

dependencies {
    implementation(project(":thrift:thrift"))
    implementation("com.linecorp.armeria:armeria-thrift0.20")
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("com.linecorp.armeria:armeria-prometheus1")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.linecorp.armeria:armeria-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
