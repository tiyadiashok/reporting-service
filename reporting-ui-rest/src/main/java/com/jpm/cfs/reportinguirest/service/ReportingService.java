package com.jpm.cfs.reportinguirest.service;

import com.jpm.cfs.reportinguirest.client.ReportingServiceClient;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RListMultimapReactive;
import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
public class ReportingService {

    @Autowired
    private ReportingServiceClient reportingServiceClient;

    private RLocalCachedMapReactive<String, List<MultiplicationTableResponse>> multiplicationTableCache;

    public ReportingService(RedissonReactiveClient client) {
        LocalCachedMapOptions<String, List<MultiplicationTableResponse>> mapOptions = LocalCachedMapOptions
                .<String, List<MultiplicationTableResponse>>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        this.multiplicationTableCache = client.getLocalCachedMap("multiplicationTable",
                new TypedJsonJacksonCodec(String.class, List.class), mapOptions);
    }

    public Flux<MultiplicationTableResponse> getMultiplicationTable(MultiplicationTableRequest request) {
        return reportingServiceClient.getMultiplicationTable(request)
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

    public Flux<Response<MultiplicationTableResponse>> getMultiplicationTableResponse(MultiplicationTableRequest request) {
        return reportingServiceClient.getMultiplicationTableResponse(request)
                //.log()
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

    public Flux<Response<MultiplicationTableResponse>> getAllMultiplicationTableResponse() {
        return reportingServiceClient.getAllMultiplicationTableResponse()
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

    public Flux<Response<MultiplicationTableResponse>> findByNumberMultiplicationTableResponse(MultiplicationTableRequest request) {
        return reportingServiceClient.findByNumberMultiplicationTableResponse(request)
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }

    public Flux<Response<MultiplicationTableResponse>> createMultiplicationTableResponse(MultiplicationTableRequest request) {
        return reportingServiceClient.createMultiplicationTableResponse(request)
                .doOnNext(response -> log.info("Received response in Service: {}", response));
    }



}
