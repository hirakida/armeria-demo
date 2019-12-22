package com.example;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerInterceptorImpl implements ServerInterceptor {
    @Override
    public <REQ, RESP> Listener<REQ> interceptCall(ServerCall<REQ, RESP> call, Metadata headers,
                                                   ServerCallHandler<REQ, RESP> next) {
        log.info("{}", headers);
        return next.startCall(call, headers);
    }
}
