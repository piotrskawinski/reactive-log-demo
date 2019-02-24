package com.example.reactivelog.demo.consumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

@Data
@Slf4j
public class DurationAwareConsumer<T> implements Consumer<T> {
    private boolean logItems;
    private long retrieved;
    private Instant start = Instant.now();
    private Instant end = Instant.now();

    public DurationAwareConsumer (boolean logItems) {
        this.logItems = logItems;
    }

    @Override
    public void accept (T data) {
        retrieved++;
        end = Instant.now();
        if (logItems) {
            log.info(data.toString() + " --> Elapsed time: " + Duration.between(start, end).toMillis());
        }
    }
}
