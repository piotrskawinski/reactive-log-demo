package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class GetLogsMultithreadingAndBenchmarking extends BaseGetLogs {

    public static void main (String[] args) {

        WebClient client = WebClient.create("http://localhost:8082");
        Instant start = now();

        Flux.interval(Duration.ofSeconds(2))
                .takeWhile(i -> i <= 10)
                .flatMap(i ->
                        Flux.range(1, 1000)
                        .flatMap(it -> {
                            log.info("Sending request ({})", it);
                            return client.get().uri("/logs")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .retrieve()
                                    .bodyToFlux(LogEvent.class);
                        }))
                .blockLast();

        logTimeInSeconds(start);
    }

}