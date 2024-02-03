package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.websocket.WebSocket;
import com.linecorp.armeria.common.websocket.WebSocketFrame;
import com.linecorp.armeria.common.websocket.WebSocketFrameType;
import com.linecorp.armeria.common.websocket.WebSocketWriter;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.websocket.WebSocketServiceHandler;

public class WebSocketServiceHandlerImpl implements WebSocketServiceHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServiceHandlerImpl.class);

    @Override
    public WebSocket handle(ServiceRequestContext ctx, WebSocket in) {
        final WebSocketWriter webSocketWriter = WebSocket.streaming();

        in.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                webSocketWriter.write("Hello!");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(WebSocketFrame webSocketFrame) {
                logger.info("type={}", webSocketFrame.type());
                if (webSocketFrame.type() == WebSocketFrameType.TEXT) {
                    webSocketWriter.write(webSocketFrame);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("error", throwable);
                webSocketWriter.close();
            }

            @Override
            public void onComplete() {
                logger.info("complete");
                webSocketWriter.close();
            }
        });

        return webSocketWriter;
    }
}
