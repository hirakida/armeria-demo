package com.example;

import java.util.List;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.hello.Hello;
import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;

@Component
public class HelloHandler implements Hello.AsyncIface {
    @Override
    public void hello1(String name, AsyncMethodCallback<String> resultHandler) {
        resultHandler.onComplete("Hello, " + name + '!');
    }

    @Override
    public void hello2(HelloRequest request, AsyncMethodCallback<HelloResponse> resultHandler) {
        HelloResponse response = new HelloResponse()
                .setMessages(List.of(String.format("Hello, %s!", request.getName())));
        resultHandler.onComplete(response);
    }
}
