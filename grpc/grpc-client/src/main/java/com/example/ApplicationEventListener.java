package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.example.Hello.HelloRequest;
import com.example.HelloServiceGrpc.HelloServiceStub;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {
    private final CalculatorServiceBlockingStub calculatorService;
    private final HelloServiceStub helloService;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        {
            CalculatorRequest request1 = CalculatorRequest.newBuilder()
                                                          .setNumber1(1)
                                                          .setNumber2(2)
                                                          .setOperation(OperationType.ADD)
                                                          .build();
            double result = calculatorService.calculate(request1).getResult();
            log.info("result={}", result);

            CalculatorRequest request2 = CalculatorRequest.newBuilder()
                                                          .setNumber1(2)
                                                          .setNumber2(3)
                                                          .setOperation(OperationType.MULTIPLY)
                                                          .build();
            result = calculatorService.calculate(request2).getResult();
            log.info("result={}", result);
        }

        {
            // hello1
            HelloRequest request1 = HelloRequest.newBuilder().setName("hirakida1").build();
            helloService.sayHello1(request1, new HelloStreamObserver());
            sleep(1);

            // hello2
            HelloRequest request2 = HelloRequest.newBuilder().setName("hirakida2").build();
            helloService.sayHello2(request2, new HelloStreamObserver());
            sleep(1);

            // hello3
            HelloRequest request3 = HelloRequest.newBuilder().setName("hirakida3").build();
            StreamObserver<HelloRequest> requestStream1 = helloService.sayHello3(new HelloStreamObserver());
            requestStream1.onNext(request3);
            requestStream1.onNext(request3);
            requestStream1.onNext(request3);
            requestStream1.onCompleted();
            sleep(1);

            // hello4
            HelloRequest request4 = HelloRequest.newBuilder().setName("hirakida4").build();
            StreamObserver<HelloRequest> requestStream2 = helloService.sayHello4(new HelloStreamObserver());
            requestStream2.onNext(request4);
            requestStream2.onNext(request4);
            requestStream2.onNext(request4);
            requestStream2.onCompleted();
            sleep(1);
        }
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("timeout", e);
        }
    }
}
