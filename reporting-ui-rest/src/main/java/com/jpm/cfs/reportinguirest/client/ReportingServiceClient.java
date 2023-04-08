package com.jpm.cfs.reportinguirest.client;

import com.jpm.cfs.reportinguirest.dto.ClientConnectionRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableRequest;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import com.jpm.cfs.reportinguirest.dto.error.ErrorEvent;
import com.jpm.cfs.reportinguirest.dto.error.StatusCode;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.loadbalance.WeightedLoadbalanceStrategy;
import io.rsocket.metadata.WellKnownMimeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ReportingServiceClient {

    private final RSocketRequester requester;

    /**
     * If the payload is not of type {@link ClientConnectionRequest}, the connection will be rejected
     * Exception thrown if parameter is not of type {@link ClientConnectionRequest} :
     * "RejectedSetupException: Could not resolve method parameter at index 0 in public reactor.core.publisher.Mono<java.lang.Void>
     *     com.jpm.cfs.reportingserver.controller.RSocketConnectionController.handleConnection(com.jpm.cfs.reportingserver.dto.ClientConnectionRequest):
     *     Payload content is missing: public reactor.core.publisher.Mono<java.lang.Void>
     *         com.jpm.cfs.reportingserver.controller.RSocketConnectionController.handleConnection(com.jpm.cfs.reportingserver.dto.ClientConnectionRequest)"
     */
    /*public ReportingServiceClient(RSocketRequester.Builder builder,
                                  RSocketConnectorConfigurer connectorConfigurer,
                                  @Value("${reporting.service.host}") String host, //Get these from Discovery
                                  @Value("${reporting.service.port}") int port) {
        ClientConnectionRequest request = new ClientConnectionRequest();
        request.setClientName("Reporting UI");
        request.setSecretKey("secret");
        this.requester = builder.rsocketConnector(connectorConfigurer)
                .setupRoute("multiplication.table.connection")
                .setupData(request)
                .tcp(host, port);
    }*/

    public ReportingServiceClient(RSocketRequester.Builder builder,
                                  RSocketConnectorConfigurer connectorConfigurer,
                                  Flux<List<LoadbalanceTarget>> targets) {
        ClientConnectionRequest request = new ClientConnectionRequest();
        request.setClientName("Reporting UI");
        request.setSecretKey("secret");

        //UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("client", "password");
        //MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        this.requester = builder
                //.setupMetadata(credentials, mimeType)
                .rsocketConnector(connectorConfigurer)
                .setupRoute("multiplication.table.connection")
                .setupData(request)
                .transports(targets, new RoundRobinLoadbalanceStrategy());
                //.transports(targets, WeightedLoadbalanceStrategy.create());

    }

    public Flux<MultiplicationTableResponse> getMultiplicationTable(MultiplicationTableRequest request) {
        return requester.route("multiplication.table")
                .data(request)
                .retrieveFlux(MultiplicationTableResponse.class)
                //retry logic
                //.onErrorReturn(MultiplicationTableRequest.create(0, 0, 0))
                //.switchIfEmpty(Flux.empty())
                .doOnNext(response -> log.info("Received response: {}", response))
                .doOnCancel(() -> log.info("Cancelled"))
                .doFinally(signalType -> log.info("Finally: {}", signalType));
    }

    public Flux<Response<MultiplicationTableResponse>> getMultiplicationTableResponse(MultiplicationTableRequest request) {
        return requester.route("multiplication.response.table")
                .data(request)
                .retrieveFlux(new ParameterizedTypeReference<Response<MultiplicationTableResponse>>() {})
                //retry logic
                //.onErrorReturn(MultiplicationTableRequest.create(0, 0, 0))
                //.switchIfEmpty(Flux.empty())
                .log()
                .doOnNext(response -> {
                    if(response.hasError())
                        log.error("Received error response: {}", response.getErrorResponse().getStatusCode().getDescription());
                    else
                        log.info("Received response: {}", response.getSuccessResponse());
                })
                //.doOnNext(response -> log.info("Received response: {}", response))
                .doOnCancel(() -> log.info("Cancelled"))
                .log()
                .doOnError(throwable -> log.error("Error: ", throwable))
                .onErrorReturn(Response.with(new ErrorEvent(StatusCode.EC003)))
                /*.onErrorResume((throwable) -> {
                    log.error("Error: {}", throwable.getMessage());
                    return Flux.just(Response.with(new ErrorEvent(StatusCode.EC003)));
                })*/
                .log()
                .doFinally(signalType -> log.info("Finally: {}", signalType));
    }
}
