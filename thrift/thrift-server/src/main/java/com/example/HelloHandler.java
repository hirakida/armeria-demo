package com.example;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.hello.Hello;

@Component
public class HelloHandler implements Hello.AsyncIface {
    @Override
    public void hello(String name, AsyncMethodCallback<String> resultHandler) {
        resultHandler.onComplete("Hello, " + name + '!');
    }
}
