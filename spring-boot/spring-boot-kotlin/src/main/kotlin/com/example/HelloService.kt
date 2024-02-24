package com.example

import com.linecorp.armeria.server.annotation.Default
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import com.linecorp.armeria.server.annotation.ProducesJson
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
@ProducesJson
class HelloService {
    data class HelloResponse(val message: String)

    @Get("/hello")
    suspend fun hello(@Param @Default("Armeria") name: String): HelloResponse {
        delay(1000)
        return HelloResponse("Hello, $name!")
    }
}
