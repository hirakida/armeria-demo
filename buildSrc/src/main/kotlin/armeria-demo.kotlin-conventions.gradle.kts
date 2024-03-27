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
    implementation(platform(libs.com.linecorp.armeria.armeria.bom))
    implementation(platform(libs.io.netty.netty.bom))
    implementation(libs.org.jetbrains.annotations)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
