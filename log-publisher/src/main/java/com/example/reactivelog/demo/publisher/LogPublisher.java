package com.example.reactivelog.demo.publisher;

import com.example.reactivelog.demo.common.LogEvent;
import com.example.reactivelog.demo.common.LogEvent.Env;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static com.example.reactivelog.demo.publisher.EventLogs.randomMessage;

@Component
@Slf4j
public class LogPublisher {

    private static WebClient client = WebClient.create("http://localhost:8080");

    public void publishLogs () {
        Instant start = now();

        Flux.range(1, 100)
                .flatMap(it -> {

                    // Each application publish log events for TEST, PREPROD, PROD
                    String app = "app_" + it;
                    Flux<LogEvent> logs = getFluxForEnv(app, Env.TEST)
                            .concatWith(getFluxForEnv(app, Env.PREPROD))
                            .concatWith(getFluxForEnv(app, Env.PROD));

                    log.info("Publishing log events for app {}", app);

                    return client.post()
                            .uri("/logs")
                            .contentType(MediaType.APPLICATION_STREAM_JSON)
                            .body(logs, LogEvent.class)
                            .retrieve()
                            .bodyToMono(Void.class);

                })

                // Normally you should never block as you block the whole main thread
                // But we do it only here just to log time
                .blockLast();

        logTime(start);
    }

    private Flux<LogEvent> getFluxForEnv (String app, Env env) {
        return Flux.interval(Duration.ofSeconds(1))
                .takeWhile(i -> i < 10)
                .map(i -> createLog(app, env));
    }

    private static LogEvent createLog (String application, Env env) {
        LogEvent logEvent = randomMessage();
        logEvent.setLogEventId(UUID.randomUUID().toString());
        logEvent.setApplication(application);
        logEvent.setEnv(env);
        logEvent.setDate(new Date(System.currentTimeMillis()));
        return logEvent;
    }

    private static Instant now () {
        return Instant.now();
    }

    private static void logTime (Instant start) {
        log.info("Elapsed time: " + Duration.between(start, Instant.now()).toMillis() + " ms");
    }

}

