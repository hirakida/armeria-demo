import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin
    id("armeria-demo.java-conventions")
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
