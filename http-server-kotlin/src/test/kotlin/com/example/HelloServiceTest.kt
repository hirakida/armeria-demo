package com.example;

import com.linecorp.armeria.client.WebClient
import com.linecorp.armeria.common.QueryParams
import com.linecorp.armeria.server.ServerBuilder
import com.linecorp.armeria.testing.junit5.server.ServerExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class HelloServiceTest {
    @RegisterExtension
    val server: ServerExtension = object : ServerExtension() {
        override fun configure(sb: ServerBuilder) {
            sb.annotatedService(HelloService());
        }

        override fun runForEachTest(): Boolean {
            return true
        }
    }

    @Test
    fun hello1() {
        val response = client().get("/hello1").aggregate().join();
        Assertions.assertEquals("{\"message\":\"Hello!\"}", response.contentUtf8());
    }

    @Test
    fun hello2() {
        val response = client().get("/hello2", QueryParams.of("name", "test")).aggregate().join();
        Assertions.assertEquals("{\"message\":\"Hello, test!\"}", response.contentUtf8());
    }

    private fun client(): WebClient = WebClient.of(server.httpUri());
}
