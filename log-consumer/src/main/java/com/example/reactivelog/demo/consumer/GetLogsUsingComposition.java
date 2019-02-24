package com.example.reactivelog.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Slf4j
public class GetLogsUsingComposition extends BaseGetLogs {

    public static void main (String[] args) throws Exception {

        Instant start = now();
        DurationAwareConsumer<String> consumer = new DurationAwareConsumer<>(true);
        // BackpressureReadySubscriber<String> consumer = new BackpressureReadySubscriber<>(true);

        getLogFlux()
                .sort((o1, o2) -> {
                    int compare = o1.getMessage().compareTo(o2.getMessage());
                    if (compare == 0) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                    return compare;
                })
                .map(it -> it.getDate() + " : " + it.getMessage())
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (o1, o2) -> o2.toString() + " : " + o1)
                .subscribe(consumer);

        logTime(1000, start, consumer);
        // blockUntilUncompletedAndLogTime(start, consumer);
    }

}

