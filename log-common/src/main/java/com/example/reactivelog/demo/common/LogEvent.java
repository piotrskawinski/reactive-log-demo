package com.example.reactivelog.demo.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data

@NoArgsConstructor
@ToString
public class LogEvent {
    protected String logEventId;
    protected String application;
    protected String message;
    protected Level level;
    protected Env env;
    protected Date date;

    @JsonCreator
    public LogEvent (@JsonProperty("logEventId") String logEventId,
                     @JsonProperty("application") String application,
                     @JsonProperty("message") String message,
                     @JsonProperty("level") Level level,
                     @JsonProperty("env") Env env,
                     @JsonProperty("date") Date date) {

        this.logEventId = logEventId;
        this.application = application;
        this.message = message;
        this.level = level;
        this.env = env;
        this.date = date;
    }

    public static enum Level {
        DEBUG, INFO, WARNING, ERROR
    }

    public static enum Env {
        DEV, TEST, PREPROD, PROD
    }
}