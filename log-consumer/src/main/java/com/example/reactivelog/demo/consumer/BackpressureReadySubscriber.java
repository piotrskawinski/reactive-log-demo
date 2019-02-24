package com.example.reactivelog.demo.consumer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class BackpressureReadySubscriber<T> extends BaseSubscriber<T> {

    private boolean logItemsOnNext;
    private String id = "default-1";
    private long received = 0;
    private long bufferCounter = 0;
    private boolean completed = false;

    public BackpressureReadySubscriber (boolean logItemsOnNext) {
        this.logItemsOnNext = logItemsOnNext;
    }

    @Override
    protected void hookOnSubscribe (Subscription subscription) {
        subscription.request(10);
        log.info("Requested 10 items on subscribe ...");
    }

    @Override
    protected void hookOnNext (T value) {
        bufferCounter++;
        if (bufferCounter >= 10) {
            // Simulate buffering ...
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bufferCounter = 0;
            upstream().request(10);
        }
        received++;
        if (logItemsOnNext) {
            log.info("{} : Item consumed -> {}", received, value.toString());
        }
    }

    /*@Override
    protected void hookOnNext (T value) {
        upstream().request(10);
        received++;
        if (logItemsOnNext) {
            log.info("{} : Item consumed -> {}", received, value.toString());
        }
    }*/

    @Override
    protected void hookOnComplete () {
        super.hookOnComplete();
        this.completed = true;
        log.info("Completed receiving {} items for '{}'", received, id);
    }
}