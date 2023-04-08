package com.jpm.cfs.reportinguirest.service;

import com.jpm.cfs.reportinguirest.client.ReportingServiceClient;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ReportingService {

    @Autowired
    private ReportingServiceClient reportingServiceClient;

    public Flux<MultiplicationTableResponse> getMultiplicationTable(MultiplicationTableRequest request) {
        return reportingServiceClient.getMultiplicationTable(request)
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

    public Flux<Response<MultiplicationTableResponse>> getMultiplicationTableResponse(MultiplicationTableRequest request) {
        return reportingServiceClient.getMultiplicationTableResponse(request)
                //.log()
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

}
