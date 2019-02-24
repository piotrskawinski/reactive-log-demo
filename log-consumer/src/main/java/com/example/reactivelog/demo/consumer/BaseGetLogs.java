package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.DetailedLogEvent;
import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Slf4j
abstract class BaseGetLogs {

    static final String BASE_URL = "http://localhost:8080";
    static WebClient client = WebClient.create(BASE_URL);

    static Flux<LogEvent> getLogFlux () {
        return getLogFlux(0);
    }

    static Flux<LogEvent> getLogFlux (long delay) {
        return client.get().uri("/logs?delay=" + delay)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(LogEvent.class);
    }

    static Mono<DetailedLogEvent> getDetailedLogMono (String id) {
        return client.get().uri("/logs/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DetailedLogEvent.class);
    }

    static void blockUntilUncompletedAndLogTime (Instant instant, BackpressureReadySubscriber subscriber) throws InterruptedException {
        while (!subscriber.isCompleted()) {
            Thread.sleep(100);
        }
        logTime(instant);
    }

    static Instant now () {
        return Instant.now();
    }

    static void logTime (Instant start) {
        log.info("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    static void logTimeInSeconds (Instant start) {
        log.info("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() / 1000 + " s");
    }

    static void logTime (long waitUntilCompletedTime, Instant start, DurationAwareConsumer consumer) throws InterruptedException {
        Thread.sleep(waitUntilCompletedTime);
        log.info("Elapsed time: " + Duration.between(start, consumer.getEnd()).toMillis() + "ms for retrieving " + consumer.getRetrieved() + " items");
    }

}