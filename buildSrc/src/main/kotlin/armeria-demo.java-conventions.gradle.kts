import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    idea
    java
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.isDeprecation = true
    options.compilerArgs.add("-parameters")
}

val libs = the<LibrariesForLibs>()

dependencies {
    implementation(platform(libs.armeria.bom))
    implementation(platform(libs.netty.bom))
    implementation(libs.jetbrains.annotations)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
