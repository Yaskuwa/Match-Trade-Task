package com.Task.Task.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscribeRequest {
    private final static String SUBSCRIBE_MSG_TYPE = "subscribe";

    private String type = SUBSCRIBE_MSG_TYPE;
    private List<String> product_ids;
    private List<String> channels;

    public SubscribeRequest(List<String> product_ids, List<String> channels) {
        this.product_ids = product_ids;
        this.channels = channels;
    }
}
