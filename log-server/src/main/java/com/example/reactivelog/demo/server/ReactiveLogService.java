
package com.example.reactivelog.demo.server;


import com.example.reactivelog.demo.common.DetailedLogEvent;
import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;
import java.util.Date;


@Service
@Slf4j
public class ReactiveLogService {

    private final ReactiveLogRepository repository;

    public ReactiveLogService (ReactiveLogRepository repository) {
        this.repository = repository;
    }

    public Flux<LogEvent> getLogsStream () {
        return this.repository.findLogsBy();
    }

    public Flux<LogEvent> getLogs (Long delay) {
        /*if (delay == null) {
            delay = 0L;
        }
        long delayPerElement = delay / 3000;
        return this.repository.findAll()
                .sort(Comparator.comparing(LogEvent::getDate))
                .map(DetailedLogEvent::asLogEvent)
                .delayElements(Duration.ofMillis(delayPerElement))
                .log();*/

        return testLogEventFlux(delay);
    }

    public Mono<DetailedLogEvent> getLog (String id) {
        return this.repository.findByLogEventId(id);
    }

    public Mono<Long> countLogs () {
        return this.repository.count();
    }

    public Mono<Void> insertLogs (Flux<DetailedLogEvent> logs) {
        return this.repository.insert(logs).then();
    }

    private static Flux<LogEvent> testLogEventFlux (Long delay) {
        if (delay == null) {
            delay = 0L;
        }
        long delayPerElement = delay / 3000;
        return Flux.range(1, 3000)
                .map(it ->
                        DetailedLogEvent.builder()
                                .logEventId(String.valueOf(it))
                                .message("Message : " + it)
                                .application("app_" + it)
                                .level(LogEvent.Level.INFO)
                                .env(LogEvent.Env.TEST)
                                .date(new Date()).build().asLogEvent()
                )
                .sort(Comparator.comparing(LogEvent::getDate))
                .delayElements(Duration.ofMillis(delayPerElement))
                .log();
    }

}
