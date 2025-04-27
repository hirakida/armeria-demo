plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.spring.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
