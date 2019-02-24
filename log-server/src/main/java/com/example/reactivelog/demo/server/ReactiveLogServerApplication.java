package com.example.reactivelog.demo.server;

import com.example.reactivelog.demo.common.DetailedLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
@Slf4j
public class ReactiveLogServerApplication {

    public static void main (String[] args) {
        SpringApplication.run(ReactiveLogServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner initMongo (MongoOperations mongo) {
        return (String... args) -> {

            mongo.dropCollection(DetailedLogEvent.class);
            mongo.createCollection(DetailedLogEvent.class, CollectionOptions.empty().size(1000000).capped());
        };
    }


}

