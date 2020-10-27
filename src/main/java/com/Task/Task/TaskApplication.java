package com.Task.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TaskApplication {

    private final static String URL = "wss://ws-feed.pro.coinbase.com";

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
