package com.example;

import java.util.List;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.hello.HelloService;

@Component
public class HelloServiceImpl implements HelloService.AsyncIface {
    @Override
    public void hello1(String name, AsyncMethodCallback<String> resultHandler) {
        resultHandler.onComplete("Hello, " + name + '!');
    }

    @Override
    public void hello2(HelloRequest request, AsyncMethodCallback<HelloResponse> resultHandler) {
        final HelloResponse response = new HelloResponse()
                .setMessages(List.of(String.format("Hello, %s!", request.getName())));
        resultHandler.onComplete(response);
    }
}
