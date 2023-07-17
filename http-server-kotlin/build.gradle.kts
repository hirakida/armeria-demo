import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies"))
    implementation("com.linecorp.armeria:armeria-kotlin")
    implementation("com.linecorp.armeria:armeria-logback")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("com.linecorp.armeria:armeria-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.platform:junit-platform-launcher")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

application {
    mainClass.set("com.example.Main")
}
