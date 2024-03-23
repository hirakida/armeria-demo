package com.example

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.logging.LoggingService

class Main

fun main() {
    val server: Server = Server.builder()
        .http(8080)
        .decorator(LoggingService.newDecorator())
        .annotatedService(HelloService())
        .build()
    server.start().join()
}
