plugins {
    id("armeria-demo.kotlin-conventions")
    application
}

dependencies {
    implementation("com.linecorp.armeria:armeria-kotlin")
    implementation("com.linecorp.armeria:armeria-logback")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("com.linecorp.armeria:armeria-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("com.example.MainKt")
}
