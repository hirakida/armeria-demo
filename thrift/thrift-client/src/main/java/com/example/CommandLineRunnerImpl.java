package com.example;

import static com.example.ArmeriaClientConfig.buildHelloClient;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.hello.Hello;
import com.example.hello.HelloRequest;
import com.example.hello.HelloResponse;
import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Calculator.AsyncIface calculatorClient;

    @Override
    public void run(String... args) throws Exception {
        AsyncMethodCallback<Integer> resultHandler = new AsyncMethodCallbackImpl();
        calculatorClient.add(1, 2, resultHandler);
        calculatorClient.calculate(1, new Work(5, 3, Operation.SUBTRACT), resultHandler);

        Hello.Iface client1 = buildHelloClient("tbinary");
        String response1 = client1.hello1("tbinary");
        log.info("{}", response1);
        HelloResponse response2 = client1.hello2(new HelloRequest("hirakida"));
        log.info("{}", response2);

        Hello.Iface client2 = buildHelloClient("tcompact");
        log.info("{}", client2.hello1("tcompact"));

        Hello.Iface client3 = buildHelloClient("tjson");
        log.info("{}", client3.hello1("tjson"));

        Hello.Iface client4 = buildHelloClient("ttext");
        log.info("{}", client4.hello1("ttext"));
    }

    private static class AsyncMethodCallbackImpl implements AsyncMethodCallback<Integer> {
        @Override
        public void onComplete(Integer response) {
            log.info("{}", response);
        }

        @Override
        public void onError(Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }
}
