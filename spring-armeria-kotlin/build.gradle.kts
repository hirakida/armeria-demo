import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    implementation("com.linecorp.armeria:armeria-spring-boot2-starter")
    implementation("com.linecorp.armeria:armeria-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
