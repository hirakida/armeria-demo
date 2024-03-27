plugins {
    `kotlin-dsl`
    id("groovy-gradle-plugin")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.plugin.spring.org.jetbrains.kotlin.plugin.spring.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
