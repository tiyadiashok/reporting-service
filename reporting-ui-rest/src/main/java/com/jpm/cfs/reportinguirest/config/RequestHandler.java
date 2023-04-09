package com.jpm.cfs.reportinguirest.config;

import com.jpm.cfs.reportinguirest.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import com.jpm.cfs.reportinguirest.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    @Autowired
    private ReportingService reportingService;


    public Mono<ServerResponse> getMultiplicationTable(ServerRequest request) {
        int number = Integer.parseInt(request.pathVariable("number"));
        int limit = Integer.parseInt(request.pathVariable("limit"));

        Flux<MultiplicationTableResponse> responseFlux = reportingService.getMultiplicationTable(MultiplicationTableRequest.create(number, limit));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, MultiplicationTableResponse.class);
    }

    public Mono<ServerResponse> getAllMultiplicationTable(ServerRequest request) {

        Flux<Response<MultiplicationTableResponse>> responseFlux = reportingService.getAllMultiplicationTableResponse();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> findByNumberMultiplicationTable(ServerRequest request) {
        int number = Integer.parseInt(request.pathVariable("number"));
        int limit = 0;

        Flux<Response<MultiplicationTableResponse>> responseFlux = reportingService
                .findByNumberMultiplicationTableResponse(MultiplicationTableRequest.create(number, limit));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> createMultiplicationTable(ServerRequest request) {
        int number = Integer.parseInt(request.pathVariable("number"));
        int limit = Integer.parseInt(request.pathVariable("limit"));

        Flux<Response<MultiplicationTableResponse>> responseFlux = reportingService.createMultiplicationTableResponse(MultiplicationTableRequest.create(number, limit));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }


}
