
package com.example.reactivelog.demo.server;


import com.example.reactivelog.demo.common.DetailedLogEvent;
import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
public class ReactiveLogController {

    private final ReactiveLogService service;

    public ReactiveLogController (ReactiveLogService service) {
        this.service = service;
    }

    @GetMapping(path = "/logs", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<LogEvent> getLogsStream (@RequestParam(required = false) Long delay) {
        log.info("Received /logs GET request : Media type {} : delay {}", MediaType.APPLICATION_STREAM_JSON_VALUE, delay);

        return service.getLogsStream();
    }

    @GetMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<LogEvent> geLogs (@RequestParam(required = false) Long delay) {
        log.info("Received /logs GET request : Media type {} : delay {}", MediaType.APPLICATION_JSON_VALUE, delay);

        return service.getLogs(delay);
    }

    @GetMapping(path = "/logs/{id}")
    public Mono<DetailedLogEvent> getLog (@PathVariable String id) {
        log.info("Received /logs/{} GET request", id);

        return service.getLog(id);
    }

    @GetMapping(path = "/logs/count")
    public Mono<Long> count () {
        log.info("Received /logs/counte GET request");

        return service.countLogs();
    }

    @PostMapping(path = "/logs", consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> insertLogs (@RequestBody Flux<DetailedLogEvent> logs) {
        log.info("Received /logs POST request : Media type {}", MediaType.APPLICATION_STREAM_JSON_VALUE);

        return service.insertLogs(logs);
    }

}