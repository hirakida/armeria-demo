plugins {
    id("java-library")
    alias(libs.plugins.com.linecorp.thrift.gradle.plugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(libs.libthrift)
    implementation(libs.javax.annotatoin.api)
    implementation(libs.slf4j.api)
}

tasks.compileJava {
    dependsOn(tasks.processResources)
}

compileThrift {
    thriftExecutable = "/opt/homebrew/bin/thrift"
    outputDir = layout.buildDirectory.dir("resources/main/META-INF/armeria/thrift")
    generator("json")
    verbose = true
}
