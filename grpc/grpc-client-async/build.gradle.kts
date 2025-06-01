plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation(project(":grpc:protobuf"))
    implementation("com.linecorp.armeria:armeria-grpc")
    implementation("com.linecorp.armeria:armeria-logback")
}

application {
    mainClass = "com.example.Main"
}
