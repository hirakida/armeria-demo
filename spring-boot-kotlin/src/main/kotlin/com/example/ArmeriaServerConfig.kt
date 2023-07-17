package com.example

import com.linecorp.armeria.server.logging.AccessLogWriter
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArmeriaServerConfig {
    @Bean
    fun armeriaServerConfigurator(helloService: HelloService): ArmeriaServerConfigurator =
        ArmeriaServerConfigurator { builder ->
            builder.annotatedService(helloService)
                .accessLogWriter(AccessLogWriter.combined(), false)
        }
}
