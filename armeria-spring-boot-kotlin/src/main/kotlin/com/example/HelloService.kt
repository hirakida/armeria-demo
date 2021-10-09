package com.example;

import com.linecorp.armeria.server.annotation.Default
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.ProducesJson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Component

@Component
@ProducesJson
class HelloService {

    @Get("/hello1")
    fun hello1() = Response("Hello!")

    @Get("/hello2")
    suspend fun hello2(@Param @Default("hirakida") name: String): Response {
        delay(1000)
        return Response("Hello, $name!")
    }

    @Get("/hello3")
    fun hello3(@Param @Default("hirakida") name: String): Flow<Response> {
        return flowOf(Response("Hello, $name!"))
    }

    data class Response(val message: String)
}
