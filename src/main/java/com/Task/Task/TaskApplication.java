package com.Task.Task;

import com.Task.Task.endpoints.WebsocketClientEndpoint;
import com.Task.Task.handlers.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@SpringBootApplication
public class TaskApplication {

	private final static String URL = "wss://ws-feed.pro.coinbase.com";

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);

		try {
			WebsocketClientEndpoint clientEndpoint = new WebsocketClientEndpoint(new URI(URL));
			clientEndpoint.setMessageHandler(new ResponseMessageHandler());
			clientEndpoint.subscribeToTicker();

		} catch (URISyntaxException ex) {
			log.error("URISyntaxException exception: " + ex.getMessage());
		}

	}
}
