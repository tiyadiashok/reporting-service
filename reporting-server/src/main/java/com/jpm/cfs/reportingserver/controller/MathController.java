package com.jpm.cfs.reportingserver.controller;

import com.jpm.cfs.reportingserver.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportingserver.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportingserver.dto.Response;
import com.jpm.cfs.reportingserver.dto.error.ErrorEvent;
import com.jpm.cfs.reportingserver.dto.error.StatusCode;
import com.jpm.cfs.reportingserver.service.MathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class MathController {

    @Autowired
    private MathService mathService;
    @MessageMapping("multiplication.table")
    public Flux<MultiplicationTableResponse> multiplicationTable(Mono<MultiplicationTableRequest> request) {
        log.info("Received request: {}", request);
        return request
                .filter(r -> r.getNumber() % 2 == 0)
                .flatMapMany(r -> mathService.multiplicationTable(r))
                .switchIfEmpty(Flux.error(new RuntimeException("No response found")))
                .doOnNext(response -> log.info("Sending response in Controller: {}", response));
    }

    @MessageMapping("multiplication.response.table")
    public Flux<Response<MultiplicationTableResponse>> multiplicationTableResponse(Mono<MultiplicationTableRequest> request) {
        log.info("Received request: {}", request);
        return request
                .filter(r -> r.getNumber() % 2 == 0)
                .flatMapMany(r -> mathService.multiplicationTable(r))
                .map(Response::with)
                .defaultIfEmpty(Response.with(new ErrorEvent(StatusCode.EC002)))
                .doOnNext(response -> log.info("Sending response in Controller: {}", response));
    }

    @MessageExceptionHandler(RuntimeException.class)
    public Flux<MultiplicationTableResponse> handleException(RuntimeException e) {
        log.error("Exception occurred: {}", e.getMessage());
        return Flux.empty();
    }

}
