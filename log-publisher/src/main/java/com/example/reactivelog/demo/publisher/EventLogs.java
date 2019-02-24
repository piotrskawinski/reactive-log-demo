package com.example.reactivelog.demo.publisher;

import com.example.reactivelog.demo.common.DetailedLogEvent;
import com.example.reactivelog.demo.common.LogEvent;
import com.example.reactivelog.demo.common.LogEvent.Level;

import java.util.HashMap;
import java.util.Map;

public class EventLogs {

    private static Map<Long, LogEvent> messageMap = new HashMap<>();

    static {
        messageMap.put(0L, DetailedLogEvent.builder().message("Cannot connect to database").level(Level.ERROR).build());
        messageMap.put(1L, DetailedLogEvent.builder().message("Item is not ").level(Level.INFO).build());
        messageMap.put(2L, DetailedLogEvent.builder().message("Variant id is not valid").level(Level.ERROR).build());
        messageMap.put(3L, DetailedLogEvent.builder().message("Risk id is not valid").level(Level.WARNING).build());
        messageMap.put(4L, DetailedLogEvent.builder().message("TIA service is unavailable").level(Level.ERROR).build());
        messageMap.put(5L, DetailedLogEvent.builder().message("The system has received order").level(Level.INFO).build());
        messageMap.put(6L, DetailedLogEvent.builder().message("Sales order found for the given id").level(Level.DEBUG).build());
    }

    public static LogEvent randomMessage () {
        long key = Math.round(6 * Math.random());
        return messageMap.get(key);
    }

}

