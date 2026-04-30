plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation("com.linecorp.armeria:armeria")
    implementation("com.linecorp.armeria:armeria-logback")
    implementation(libs.spotbugs.annotations)
}

application {
    mainClass = "com.example.Main"
}
