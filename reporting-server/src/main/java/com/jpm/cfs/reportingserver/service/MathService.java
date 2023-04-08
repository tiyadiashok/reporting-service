package com.jpm.cfs.reportingserver.service;

import com.jpm.cfs.reportingserver.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Slf4j
public class MathService {

    public Flux<MultiplicationTableResponse> multiplicationTable(MultiplicationTableRequest request) {
        return Flux.range(1, request.getLimit())
                .delayElements(Duration.ofSeconds(1))
                .map(i -> MultiplicationTableResponse.create(request.getNumber(), i, request.getNumber() * i))
                .doOnNext(response -> log.info("Sending response: {}", response));
    }
}
