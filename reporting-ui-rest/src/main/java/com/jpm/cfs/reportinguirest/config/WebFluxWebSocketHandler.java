package com.jpm.cfs.reportinguirest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpm.cfs.reportinguirest.dto.MultiplicationTableResponse;
import com.jpm.cfs.reportinguirest.dto.Response;
import com.jpm.cfs.reportinguirest.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class WebFluxWebSocketHandler implements WebSocketHandler {

    @Autowired
    private ReportingService reportingService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        Flux<Response<MultiplicationTableResponse>> responseFlux = reportingService.getAllMultiplicationTableResponse();
        return session.send(responseFlux
                .delayElements(Duration.ofSeconds(1))
                .map(this::convertToJSON)
                .map(session::textMessage));
    }

    private String convertToJSON(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
