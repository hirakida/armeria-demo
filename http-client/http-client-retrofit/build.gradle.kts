plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation("com.linecorp.armeria:armeria-retrofit2")
    implementation("com.linecorp.armeria:armeria-logback")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation(libs.retrofit2.adapter.rxjava3)
    implementation(libs.retrofit2.converter.jackson)
}

application {
    mainClass = "com.example.Main"
}
