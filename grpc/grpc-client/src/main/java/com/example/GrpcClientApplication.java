package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.example.Hello.HelloRequest;
import com.example.HelloServiceGrpc.HelloServiceStub;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingClient;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GrpcClientApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        calculator();
        hello();
    }

    private static void calculator() {
        CalculatorServiceBlockingStub calculatorService = Clients.builder("gproto+http://127.0.0.1:8080/")
                                                                 .responseTimeoutMillis(10000)
                                                                 .decorator(LoggingClient.newDecorator())
                                                                 .build(CalculatorServiceBlockingStub.class);

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

    private static void hello() throws InterruptedException, ExecutionException {
        HelloServiceStub helloService = Clients.builder("gproto+http://127.0.0.1:8080/")
                                               .responseTimeoutMillis(10000)
                                               .decorator(LoggingClient.newDecorator())
                                               .build(HelloServiceStub.class);

        // hello1
        HelloRequest request1 = HelloRequest.newBuilder().setName("hirakida1").build();
        helloService.sayHello1(request1, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // hello2
        HelloRequest request2 = HelloRequest.newBuilder().setName("hirakida2").build();
        helloService.sayHello2(request2, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // hello3
        HelloRequest request3 = HelloRequest.newBuilder().setName("hirakida3").build();
        StreamObserver<HelloRequest> requestStream1 = helloService.sayHello3(new HelloStreamObserver());
        requestStream1.onNext(request3);
        requestStream1.onNext(request3);
        requestStream1.onCompleted();
        TimeUnit.SECONDS.sleep(1);

        // hello4
        HelloRequest request4 = HelloRequest.newBuilder().setName("hirakida4").build();
        StreamObserver<HelloRequest> requestStream2 = helloService.sayHello4(new HelloStreamObserver());
        requestStream2.onNext(request4);
        requestStream2.onNext(request4);
        requestStream2.onCompleted();
        TimeUnit.SECONDS.sleep(1);
    }

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }
}
