package com.example;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.client.websocket.WebSocketClient;
import com.linecorp.armeria.common.websocket.WebSocket;
import com.linecorp.armeria.common.websocket.WebSocketFrame;
import com.linecorp.armeria.common.websocket.WebSocketFrameType;
import com.linecorp.armeria.common.websocket.WebSocketWriter;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final Subscriber<WebSocketFrame> subscriber = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(WebSocketFrame webSocketFrame) {
                logger.info("type={}", webSocketFrame.type());
                if (webSocketFrame.type() == WebSocketFrameType.TEXT) {
                    logger.info("text={}", webSocketFrame.text());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("error", throwable);
            }

            @Override
            public void onComplete() {
                logger.info("complete");
            }
        };

        final WebSocketClient client = WebSocketClient.of("ws://localhost:8080");
        client.connect("/ws")
              .thenAccept(session -> {
                  final WebSocketWriter writer = WebSocket.streaming();
                  session.setOutbound(writer);
                  writer.write("Hello from client");
                  writer.close();
                  session.inbound().subscribe(subscriber);
              });

        TimeUnit.SECONDS.sleep(3);
    }
}
