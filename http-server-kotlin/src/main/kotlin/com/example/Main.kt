package com.example

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.logging.AccessLogWriter
import com.linecorp.armeria.server.logging.LoggingService

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val server: Server = Server.builder()
            .http(8080)
            .decorator(LoggingService.newDecorator())
            .accessLogWriter(AccessLogWriter.combined(), false)
            .annotatedService(HelloService())
            .build()
        server.start().join()
    }
}
