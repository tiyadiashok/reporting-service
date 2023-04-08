package com.jpm.cfs.reportingserver.controller;

import com.jpm.cfs.reportingserver.dto.ClientConnectionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class RSocketConnectionController {

    /**
     * This method is called when the RSocket client connects to the RSocket server for every new connection
     * If the payload is not of type {@link ClientConnectionRequest}, the connection will be rejected
     * Exception thrown if parameter is not of type {@link ClientConnectionRequest} :
     * "org.springframework.messaging.handler.invocation.MethodArgumentResolutionException:
     * Could not resolve method parameter at index 0 in public reactor.core.publisher.Mono<java.lang.Void>
     *     com.jpm.cfs.reportingserver.controller.RSocketConnectionController.handleConnection(com.jpm.cfs.reportingserver.dto.ClientConnectionRequest)
     *     : Payload content is missing: public reactor.core.publisher.Mono<java.lang.Void>
     *         com.jpm.cfs.reportingserver.controller.RSocketConnectionController.handleConnection(com.jpm.cfs.reportingserver.dto.ClientConnectionRequest)"
     * TODO: Add authentication logic here
     * TODO: Add authorization logic here
     * TODO: Add connection pooling logic here
     * TODO: Add exception handling logic here
     * TODO: Add metrics here
     * TODO: Add logging here
     *
     */
    @ConnectMapping("multiplication.table.connection")
    public Mono<Void> handleMultiplicationConnection(ClientConnectionRequest request, RSocketRequester rSocketRequester) {
        log.info("Received multiplication connection request : {}", request);
        return request.getSecretKey().equals("secret") ? Mono.empty() :
                Mono.error(new RuntimeException("Invalid secret key"));
    }

    @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequest request, RSocketRequester rSocketRequester) {
        log.info("Received connection request : {}", request);
        return request.getSecretKey().equals("secret") ? Mono.empty() :
                Mono.fromRunnable(() -> rSocketRequester.rsocket().dispose());
        //Mono.error(new RuntimeException("Invalid secret key"));
    }
}
