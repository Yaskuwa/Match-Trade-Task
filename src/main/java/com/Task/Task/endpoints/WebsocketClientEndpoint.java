package com.Task.Task.endpoints;


import com.Task.Task.handlers.ChannelType;
import com.Task.Task.handlers.Instrument;
import com.Task.Task.handlers.MessageHandler;
import com.Task.Task.messages.SubscribeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import java.net.URI;
import java.util.Arrays;


@Slf4j
@ClientEndpoint
@Service
public class WebsocketClientEndpoint {
    private final static String URL = "wss://ws-feed.pro.coinbase.com";

    private Session session;
    private final MessageHandler messageHandler;
    private final ObjectMapper mapper;

    public WebsocketClientEndpoint(MessageHandler messageHandler, ObjectMapper mapper) {
        this.mapper = mapper;
        this.messageHandler = messageHandler;
        connectToServer();
    }

    private void connectToServer() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(URL));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        subscribeToTicker();
        log.info("Session open");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.session = null;
        log.info("Session closed");
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public void sendMessageAsJson(Object message) {
        try {
            this.session.getAsyncRemote().sendText(mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void subscribeToTicker() {
        String[] channels = {ChannelType.ticker.toString()};
        String[] productIds = {Instrument.BTCEUR.getProductId(), Instrument.BTCUSD.getProductId(),
                Instrument.ETHEUR.getProductId(), Instrument.ETHUSD.getProductId()};
        SubscribeRequest request = new SubscribeRequest(Arrays.asList(productIds), Arrays.asList(channels));
        sendMessageAsJson(request);
    }

}
