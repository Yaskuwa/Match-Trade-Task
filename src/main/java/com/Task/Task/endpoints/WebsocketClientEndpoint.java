package com.Task.Task.endpoints;


import com.Task.Task.handlers.ChannelType;
import com.Task.Task.handlers.Instrument;
import com.Task.Task.handlers.MessageHandler;
import com.Task.Task.messages.SubscribeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.*;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


@Slf4j
@ClientEndpoint
public class WebsocketClientEndpoint {

    private Session session;
    private MessageHandler messageHandler;
    private final ObjectMapper mapper = new ObjectMapper();

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
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

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
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
