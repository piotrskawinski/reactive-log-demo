package com.example.reactivelog.demo.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class GetLogsCounted extends BaseGetLogs {

    public static void main (String[] args) {

        Optional<Long> count = client.get().uri("/logs/count")
                .retrieve()
                .bodyToMono(Long.class).blockOptional();

        log.info("Logs : " + count.get());
    }


}

