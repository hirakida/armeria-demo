plugins {
    id("armeria-demo.java-conventions")
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.io.spring.dependency.management)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    modules {
        module("org.springframework.boot:spring-boot-starter-tomcat") {
            replacedBy("org.springframework.boot:spring-boot-starter-jetty", "Use Jetty instead of Tomcat")
        }
    }
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("com.linecorp.armeria:armeria-jetty12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
