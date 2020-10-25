package com.Task.Task.handlers;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Instrument {
    BTCUSD("BTC-USD"),
    BTCEUR("BTC-EUR"),
    ETHUSD("ETH-USD"),
    ETHEUR("ETH-EUR");

    private static final Map<String, Instrument> map = new HashMap<>(values().length, 1);

    static {
        for (Instrument c : values()) map.put(c.productId, c);
    }

    private String productId;

    Instrument(String productId) {
        this.productId = productId;
    }

    public static Instrument of(String productId) {
        Instrument result = map.get(productId);
        if (result == null) {
            throw new IllegalArgumentException("Invalid productId: " + productId);
        }
        return result;
    }
}
