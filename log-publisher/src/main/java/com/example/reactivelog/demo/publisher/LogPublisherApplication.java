package com.example.reactivelog.demo.publisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class LogPublisherApplication implements CommandLineRunner {

    private LogPublisher logPublisher;

    public static void main (String[] args) {
        SpringApplication.run(LogPublisherApplication.class, args);
    }

    @Override
    public void run (String... args) {
        logPublisher.publishLogs();
    }
}

