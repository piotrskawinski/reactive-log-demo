package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.DetailedLogEvent;
import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;

@Slf4j
public class GetLogsUsingParallelization extends BaseGetLogs {

    public static void main (String[] args) throws InterruptedException {

        Instant start = now();
        DurationAwareConsumer<DetailedLogEvent> consumer = new DurationAwareConsumer<>(true);
        Flux.just(LogEvent.Level.DEBUG, LogEvent.Level.INFO, LogEvent.Level.WARNING)
                .flatMap(level ->
                        getLogFlux(0).filter(it -> it.getLevel().equals(level))
                )
                .parallel(4)
                .runOn(Schedulers.parallel())
                .map(GetLogsUsingParallelization::retrieveBlockingDetailedLogEvent)
                .subscribe(consumer);

        logTime(10 * 1000, start, consumer);
    }

    /** Assume that one is blocking */
    private static DetailedLogEvent retrieveBlockingDetailedLogEvent (LogEvent it) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<DetailedLogEvent> response = restTemplate.getForEntity(BASE_URL + "/logs/" + it.getLogEventId(), DetailedLogEvent.class);
            return response.getBody();
        } catch (RestClientException e) {
            String message = "Failed when retrieving detailed log event : " + e.getMessage();
            log.error(message);
            return DetailedLogEvent.builder().logEventId(it.getLogEventId()).message(message).build();
        }
    }

}

