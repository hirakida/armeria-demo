plugins {
    id("armeria-demo.java-conventions")
    id("application")
}

dependencies {
    implementation("com.linecorp.armeria:armeria-graphql")
    implementation("com.linecorp.armeria:armeria-logback")
}

application {
    mainClass = "com.example.Main"
}
