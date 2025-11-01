import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin
    id("armeria-demo.java-conventions")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xjavac-arguments='-Xlint:deprecation'"
        )
    }
}

val libs = the<LibrariesForLibs>()

dependencies {
    implementation(platform(libs.kotlinx.coroutines.bom))
}
