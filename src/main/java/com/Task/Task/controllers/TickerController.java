package com.Task.Task.controllers;

import com.Task.Task.handlers.Instrument;
import com.Task.Task.messages.TickerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class TickerController {
    @Getter
    private final static Map<Instrument, TickerResponse> lastResponses = new HashMap<>();

    private final ObjectMapper mapper;

    public TickerController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/ticker")
    public String sendLastTickerResponses() throws JsonProcessingException {
        return mapper.writeValueAsString(lastResponses.values());
    }

    public static void updateLatestResponses(Instrument instrument, TickerResponse tickerResponse) {
        lastResponses.put(instrument, tickerResponse);
    }
}

