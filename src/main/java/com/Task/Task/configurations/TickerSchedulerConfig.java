package com.Task.Task.configurations;

import com.Task.Task.controllers.TickerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class TickerSchedulerConfig {

    @Autowired
    SimpMessagingTemplate template;

    ObjectMapper mapper = new ObjectMapper();

    @Scheduled(fixedDelay = 3000)
    public void sendMessage() throws JsonProcessingException {
        template.convertAndSend("/topic/ticker",
                mapper.writeValueAsString(TickerController.getLastResponses().values()));
    }
}
