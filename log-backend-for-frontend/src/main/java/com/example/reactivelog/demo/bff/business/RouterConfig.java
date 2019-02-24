package com.example.reactivelog.demo.bff.business;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
@Slf4j
public class RouterConfig {

    @Bean
    public RouterFunction<?> routes (LogEventHandler eventHandler) {
        return RouterFunctions.route()
                .GET("/logs", eventHandler::getAllLogs)
                .GET("/logs/{app}", eventHandler::getLogsByFilter)
                .build();
    }

}