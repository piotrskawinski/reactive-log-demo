package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.LogEvent;
import com.example.reactivelog.demo.common.LogEvent.Env;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class GetLogsUsingFilter extends BaseGetLogs {

    public static void main (String[] args) throws InterruptedException {

        Instant start = now();
        DurationAwareConsumer<LogEvent> consumer = new DurationAwareConsumer<>(true);
        // BackpressureReadySubscriber<LogEvent> consumer = new BackpressureReadySubscriber<>(true);

        getLogFlux()
                .filter(it -> it.getEnv().equals(Env.PROD))
                .subscribe(consumer);

        logTime(1000, start, consumer);
        // blockUntilUncompletedAndLogTime(start, consumer);
    }


}

