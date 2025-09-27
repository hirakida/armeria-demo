plugins {
    id("armeria-demo.spring-boot-conventions")
    id("armeria-demo.kotlin-conventions")
    kotlin("plugin.spring")
}

dependencies {
    implementation("com.linecorp.armeria:armeria-spring-boot3-starter")
    implementation("com.linecorp.armeria:armeria-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
