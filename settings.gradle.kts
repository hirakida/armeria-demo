pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

rootProject.name = "armeria-demo"

include(
    ":graphql",
    ":grpc:grpc-client-async",
    ":grpc:grpc-client-blocking",
    ":grpc:grpc-client-future",
    ":grpc:grpc-server",
    ":grpc:grpc-web",
    ":grpc:protobuf",
    ":http-client:http-client",
    ":http-client:http-client-async-loader",
    ":http-client:http-client-circuit-breaker",
    ":http-client:http-client-decorator",
    ":http-client:http-client-load-balancing",
    ":http-client:http-client-retrofit",
    ":http-client:http-client-retry",
    ":http-metrics",
    ":http-server:http-server",
    ":http-server:http-server-auth",
    ":http-server:http-server-decorator",
    ":http-server:http-server-kotlin",
    ":http-server:http-server-logback",
    ":http-server:http-server-reactor",
    ":http-server:http-server-rxjava",
    ":http-server:http-server-throttling",
    ":spring-boot:spring-boot",
    ":spring-boot:spring-boot-actuator",
    ":spring-boot:spring-boot-jetty",
    ":spring-boot:spring-boot-kotlin",
    ":spring-boot:spring-boot-ssl",
    ":spring-boot:spring-boot-tomcat",
    ":spring-boot:spring-boot-webflux",
    ":thrift:thrift",
    ":thrift:thrift-client-async",
    ":thrift:thrift-client-sync",
    ":thrift:thrift-server",
    ":websocket:websocket-client",
    ":websocket:websocket-server"
)
