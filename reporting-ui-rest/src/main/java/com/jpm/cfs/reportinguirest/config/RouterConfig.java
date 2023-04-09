package com.jpm.cfs.reportinguirest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {

        return RouterFunctions.route()
                .GET("reporting/router/multiplication-table/{number}/{limit}", requestHandler::getMultiplicationTable)
                .GET("reporting/router/multiplication-table", requestHandler::getAllMultiplicationTable)
                .GET("reporting/router/multiplication-table/{number}", requestHandler::findByNumberMultiplicationTable)
                .POST("reporting/router/multiplication-table/{number}/{limit}", requestHandler::createMultiplicationTable)
                .build();
    }
}
