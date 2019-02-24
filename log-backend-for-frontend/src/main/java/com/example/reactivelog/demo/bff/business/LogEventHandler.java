package com.example.reactivelog.demo.bff.business;


import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogEventHandler {

    private static WebClient client = WebClient.create("http://localhost:8080");

    // private long numberOfRequest = 0;

    public Mono<ServerResponse> getAllLogs (ServerRequest request) {
        // log.info("Received request {}", ++numberOfRequest);

        Flux<LogEvent> flux = client.get().uri("/logs?delay=3000")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(LogEvent.class)

                // Get only 1000 of entries
                .take(1000);

        return serverResponseMono(flux);
    }

    public Mono<ServerResponse> getLogsByFilter (ServerRequest request) {
        // Read request variables/parameters
        final String app = request.pathVariable("app");
        final String env = request.queryParam("env").isPresent() ? request.queryParam("env").get() : null;
        final String level = request.queryParam("level").isPresent() ? request.queryParam("level").get() : null;

        // Create flux
        Flux<LogEvent> flux = client.get().uri("/logs")
                .retrieve()
                .bodyToFlux(LogEvent.class)
                .filter(it -> {
                    boolean filter = it.getApplication().equals(app);
                    if (env != null) {
                        filter = filter && it.getEnv().name().equalsIgnoreCase(env);
                    }
                    if (level != null) {
                        filter = filter && it.getLevel().name().equalsIgnoreCase(level);
                    }
                    return filter;
                });

        return serverResponseMono(flux);
    }

    private Mono<ServerResponse> serverResponseMono (Flux<LogEvent> flux) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux, LogEvent.class);
    }

}