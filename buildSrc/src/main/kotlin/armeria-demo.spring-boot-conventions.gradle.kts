import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("armeria-demo.java-conventions")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

val libs = the<LibrariesForLibs>()

dependencyManagement {
    extra["netty.version"] = libs.versions.netty.get()
}
