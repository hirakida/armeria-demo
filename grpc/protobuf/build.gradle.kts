import com.google.protobuf.gradle.id

plugins {
    id("java-library")
    alias(libs.plugins.com.google.protobuf)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(platform(libs.grpc.bom))
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation(libs.protobuf.java)
    implementation(libs.javax.annotatoin.api)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.java.get()}"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}
