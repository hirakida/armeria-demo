plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation("com.linecorp.armeria:armeria-rxjava3")
    implementation("com.linecorp.armeria:armeria-logback")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

application {
    mainClass = "com.example.Main"
}
