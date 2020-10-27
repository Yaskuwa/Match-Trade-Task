package com.Task.Task.configurations;

import com.Task.Task.controllers.TickerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class TickerSchedulerConfig {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public TickerSchedulerConfig(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendMessage() throws JsonProcessingException {
        template.convertAndSend("/topic/ticker",
                mapper.writeValueAsString(TickerController.getLastResponses().values()));
    }
}
