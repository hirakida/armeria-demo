import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
                "-Xjsr305=strict",
                "-Xjavac-arguments='-Xlint:deprecation'"
        )
    }
}

val libs = the<LibrariesForLibs>()

dependencies {
    implementation(platform(libs.armeria))
    implementation(platform(libs.netty))
    implementation(libs.jetbrains.annotations)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
