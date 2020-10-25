package com.Task.Task.handlers;

import com.Task.Task.controllers.TickerController;
import com.Task.Task.messages.TickerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseMessageHandler implements MessageHandler {

    private final static String PRODUCT_ID = "product_id";
    private final static String BEST_BID = "best_bid";
    private final static String BEST_ASK = "best_ask";
    private final static String PRICE = "price";
    private final static String TIME = "time";
    private final static String TYPE = "type";

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleMessage(String message) {
        try {
            Map<String, String> messageAsMap = mapper.readValue(message, HashMap.class);
            String type = messageAsMap.get(TYPE);
            if (type.equals(ChannelType.ticker.toString())) {
                TickerResponse tickerResponse = convertToTicker(messageAsMap);
                TickerController.updateLatestResponses(tickerResponse.getInstrument(), tickerResponse);
                log.info(mapper.writeValueAsString(tickerResponse));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private TickerResponse convertToTicker(Map<String, String> messageAsMap) {
        Instrument instrument = Instrument.of(messageAsMap.get(PRODUCT_ID));
        String bid = messageAsMap.get(BEST_BID);
        String ask = messageAsMap.get(BEST_ASK);
        String last = messageAsMap.get(PRICE);
        String time = messageAsMap.get(TIME);
        time = time.substring(time.indexOf("T") + 1, time.indexOf("."));
        return new TickerResponse(instrument, bid, ask, last, time);
    }
}
