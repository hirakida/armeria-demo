import com.google.protobuf.gradle.id

plugins {
    id("java-library")
    alias(libs.plugins.com.google.protobuf)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)
    implementation(libs.protobuf.java)
    implementation(libs.javax.annotatoin.api)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    plugins {
        id("grpc") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
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
