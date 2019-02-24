package com.example.reactivelog.demo.consumer;

import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.Instant;

@Slf4j
public class GetLogs extends BaseGetLogs {

    public static void main (String[] args) throws InterruptedException {

        getLogFlux()
                .subscribe(new EventLogSubsciber());

        Thread.sleep(5000);
    }

    private static class EventLogSubsciber implements Subscriber<LogEvent> {
        Subscription subscription;

        @Override
        public void onSubscribe (Subscription s) {
            this.subscription = s;
            subscription.request(10);
            log.info("Requested 10 items on subscribe ...");
        }

        @Override
        public void onNext (LogEvent logEvent) {
            subscription.request(1);
            log.info("Requested 1 item on onNext ..." + logEvent.toString());
        }

        @Override
        public void onError (Throwable t) {

        }

        @Override
        public void onComplete () {
            log.info("Completed ...");
        }
    }

}

