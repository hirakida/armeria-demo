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
    implementation(libs.kotlin)
    implementation(libs.kotlin.spring)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
