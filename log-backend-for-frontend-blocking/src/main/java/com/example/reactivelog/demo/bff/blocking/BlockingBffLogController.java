
package com.example.reactivelog.demo.bff.blocking;


import com.example.reactivelog.demo.common.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class BlockingBffLogController {

    private long numberOfRequest = 0;

    @GetMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogEvent[]> geLogs () {
        log.info("Received request {}", ++numberOfRequest);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LogEvent[]> response = restTemplate.getForEntity("http://localhost:8080/logs?delay=3000", LogEvent[].class);
        return ResponseEntity.ok(response.getBody());
    }

}
