package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.LogEvent;
import com.example.reactivelog.demo.common.LogEvent.Env;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class GetLogsUsingBackpressure extends BaseGetLogs {

    public static void main (String[] args) throws InterruptedException {

        Instant start = now();
        BackpressureReadySubscriber<LogEvent> subscriber = new BackpressureReadySubscriber<>(true);

        getLogFlux()
                .filter(it -> it.getEnv().equals(Env.PROD))
                .subscribe(subscriber);

        blockUntilUncompletedAndLogTime(start, subscriber);
    }


}

