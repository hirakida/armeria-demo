import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin
    id("armeria-demo.java-conventions")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xjavac-arguments='-Xlint:deprecation'"
        )
    }
}
