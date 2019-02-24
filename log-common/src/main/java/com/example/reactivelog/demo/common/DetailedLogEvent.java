package com.example.reactivelog.demo.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class DetailedLogEvent extends LogEvent {
    private Exception stackTrace;

    @Builder
    @JsonCreator
    public DetailedLogEvent (@JsonProperty("logEventId") String logEventId,
                             @JsonProperty("application") String application,
                             @JsonProperty("message") String message,
                             @JsonProperty("level") Level level,
                             @JsonProperty("env") Env env,
                             @JsonProperty("date") Date date,
                             @JsonProperty("stackTrace") Exception stackTrace) {

        super(logEventId, application, message, level, env, date);
        this.stackTrace = stackTrace;
    }

    public LogEvent asLogEvent () {
        return new LogEvent(logEventId, application, message, level, env, date);
    }
}