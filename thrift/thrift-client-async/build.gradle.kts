plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation(project(":thrift:thrift"))
    implementation("com.linecorp.armeria:armeria")
    implementation("com.linecorp.armeria:armeria-thrift0.20")
    implementation("com.linecorp.armeria:armeria-logback")
}

application {
    mainClass = "com.example.Main"
}
