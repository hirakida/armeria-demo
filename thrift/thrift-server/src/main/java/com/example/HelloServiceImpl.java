package com.example;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloService;

@Component
public class HelloServiceImpl implements HelloService.AsyncIface {
    private final Random random = new Random();

    @Override
    public void hello1(String name, AsyncMethodCallback<String> resultHandler) {
        resultHandler.onComplete("Hello, " + name + '!');
    }

    @Override
    public void hello2(HelloRequest request, AsyncMethodCallback<HelloResponse> resultHandler) {
        final String message = String.format("Hello, %s!", request.getName());
        final HelloResponse response = new HelloResponse()
                .setMessage(message)
                .setMessages(List.of(message))
                .setEpochMilli(Instant.now().toEpochMilli());
        if (random.nextBoolean()) {
            response.setHasOptionalMessage(true)
                    .setOptionalMessage(message);
        }
        resultHandler.onComplete(response);
    }
}
