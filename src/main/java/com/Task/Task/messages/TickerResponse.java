package com.Task.Task.messages;

import com.Task.Task.handlers.Instrument;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerResponse {
    private Instrument instrument;
    private String bid;
    private String ask;
    private String last;
    private String time;

    public TickerResponse(Instrument instrument, String bid, String ask, String last, String time) {
        this.instrument = instrument;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
        this.time = time;
    }
}
