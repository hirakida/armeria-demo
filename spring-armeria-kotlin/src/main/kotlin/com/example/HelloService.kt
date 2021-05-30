package com.example;


import com.linecorp.armeria.server.annotation.Default
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.ProducesJson
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class HelloService {

    @Get("/hello1")
    fun hello1(): String = "Hello!"

    @Get("/hello2")
    @ProducesJson
    suspend fun hello2(@Param @Default("hirakida") name: String): Response =
        delayedHello(name)

    private suspend fun delayedHello(name: String): Response {
        delay(1000)
        return Response("Hello, $name!")
    }

    data class Response(val message: String)
}
