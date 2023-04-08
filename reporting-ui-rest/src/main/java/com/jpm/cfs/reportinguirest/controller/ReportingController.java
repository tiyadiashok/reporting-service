package com.jpm.cfs.reportinguirest.controller;

import com.jpm.cfs.reportinguirest.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import com.jpm.cfs.reportinguirest.service.ReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reporting")
@Slf4j
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    @GetMapping(value = "/multiplication-table/{number}/{limit}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MultiplicationTableResponse> multiplicationTable(@PathVariable int number, @PathVariable int limit) {
        return reportingService.getMultiplicationTable(MultiplicationTableRequest.create(number, limit))
                .doOnNext(response -> log.info("Received response in Controller: {} ", response));
    }

    @PostMapping(value = "/multiplication-table", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MultiplicationTableResponse> multiplicationTable(@RequestBody Mono<MultiplicationTableRequest> requestMono) {
        return requestMono
                .filter(request -> request.getNumber() > 0 && request.getLimit() > 0)
                .flatMapMany(request -> reportingService.getMultiplicationTable(request))
                .doOnNext(response -> log.info("Received response in Controller: {} ", response))
                .doOnDiscard(MultiplicationTableResponse.class, response -> log.info("Discarded response: {} ", response));
    }

    @GetMapping(value = "/multiplication-response-table/{number}/{limit}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response<MultiplicationTableResponse>> multiplicationTableResponse(@PathVariable int number, @PathVariable int limit) {
        return reportingService.getMultiplicationTableResponse(MultiplicationTableRequest.create(number, limit))
                .doOnNext(response -> log.info("Received response in Controller: {} ", response));
    }
}
