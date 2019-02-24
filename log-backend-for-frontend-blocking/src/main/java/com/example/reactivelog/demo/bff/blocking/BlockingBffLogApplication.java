package com.example.reactivelog.demo.bff.blocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BlockingBffLogApplication {

    public static void main (String[] args) {
        SpringApplication.run(BlockingBffLogApplication.class, args);
    }

}

