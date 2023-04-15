package com.jpm.cfs.reportinguirest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@Configuration
public class WebFluxConfig {

    @Autowired
    private WebFluxWebSocketHandler webFluxWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebFluxWebSocketHandler> handlerMap = Map.of(
                "/reporting/webflux/multiplication-table", webFluxWebSocketHandler
        );
        return new SimpleUrlHandlerMapping(handlerMap, 1);
    }
}
