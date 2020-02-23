package com.example.service;

import org.springframework.stereotype.Service;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorResponse;
import com.example.CalculatorServiceGrpc.CalculatorServiceImplBase;

import io.grpc.stub.StreamObserver;

@Service
public class CalculatorService extends CalculatorServiceImplBase {

    @Override
    public void calculate(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        CalculatorResponse.Builder builder = CalculatorResponse.newBuilder();
        switch (request.getOperation()) {
            case ADD:
                builder.setResult(request.getNumber1() + request.getNumber2());
                break;
            case SUBTRACT:
                builder.setResult(request.getNumber1() - request.getNumber2());
                break;
            case MULTIPLY:
                builder.setResult(request.getNumber1() * request.getNumber2());
                break;
            case DIVIDE:
                builder.setResult(request.getNumber1() / request.getNumber2());
                break;
            case UNRECOGNIZED:
                break;
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
